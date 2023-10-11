import { Component,OnInit} from '@angular/core';
import { BehaviorSubject, Observable, catchError, map, of, startWith, tap } from 'rxjs';
import { AppState } from './interface/app-state';
import { CustomResponse } from './interface/custom-response';
import { UserService } from 'src/service/user.service';
import { DataState } from './enum/data-state.enum';
import { NgForm } from '@angular/forms';
import { User } from './interface/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'front-end';
  appState$!: Observable<AppState<CustomResponse>>;
  private dataSubject=new BehaviorSubject<CustomResponse| null>(null);
  private isLoading=new  BehaviorSubject<boolean>(false);
  readonly DataState=DataState

  constructor(private userService:UserService){}

  ngOnInit(): void {
    const limit=3;
   this.appState$=this.userService.users$(limit)
   .pipe(
    map(response =>{
      this.dataSubject.next(response)
      // return {dataState:DataState.LOADED_STATE,appData:{...response,data:{users:response.data.users}}}
      return {dataState:DataState.LOADED_STATE,appData:{...response}}
    }),
    startWith({dataState:DataState.LOADED_STATE}),
    catchError((error:string)=>{
      return of({dataState:DataState.ERROR_STATE,error})
    })
   );
  }

  saveUser(userForm:NgForm):void{
    this.isLoading.next(true); 
    this.appState$=this.userService.save$(userForm.value as User)
    .pipe(
      map(response=>{
        const existingUsers=this.dataSubject.value?.data.users || [];
        if(response.data.user){existingUsers.push(response.data.user)}
        this.dataSubject.next(
          {...response,data:{users:existingUsers}}
      );
        document.getElementById('closeModal')?.click();
        this.isLoading.next(false);
        userForm.resetForm();
        return {dataState:DataState.LOADED_STATE,appData:this.dataSubject.value};
        }),
      startWith({dataState:DataState.LOADED_STATE,appData:this.dataSubject.value}),
      catchError((error:string)=>{
        this.isLoading.next(false);
        return of({dataState:DataState.ERROR_STATE,error})
      })
    );  
  }

  //Function to delete the user
  deleteUser(user:User): void{
    this.appState$=this.userService.delete$(user.id)
    .pipe(
      map(response=>{
        this.dataSubject.next(
          {...response,data:{
            users:this.dataSubject.value?.data.users?.filter(s=> s.id!==user.id)}}
        )
        return {dataState:DataState.LOADED_STATE,appData:this.dataSubject.value}
      }),
      startWith({dataState:DataState.LOADED_STATE,appData:this.dataSubject.value}),
      catchError((error:string)=>{
        return of({dataState:DataState.ERROR_STATE,error})
      }))
  }
  
}
