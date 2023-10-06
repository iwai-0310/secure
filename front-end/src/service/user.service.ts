import {HttpClient,HttpErrorResponse, HttpParams} from '@angular/common/http'
import { Injectable } from '@angular/core';
import { Observable, ObservedValueOf, catchError, tap, throwError } from 'rxjs';
import { CustomResponse } from 'src/app/interface/custom-response';
import { User } from 'src/app/interface/user';
@Injectable({
    providedIn:'root',
})

export class UserService{
    //Store backend url into apiUrl varaiable
    private readonly apiUrl='http://localhost:8080';
    //Inject Httpclient into constructor
    constructor(private http:HttpClient){}

    //User$ observable to subscribe to the get user list
    // users$= <Observable<CustomResponse>>
    // this.http.get<CustomResponse>(`${this.apiUrl}/user/list`)
    // .pipe(
    //     tap(console.log),
    //     catchError(this.handleError)
    // );
        users$(limit:number): Observable<CustomResponse> {
        const params = new HttpParams().set('limit', limit.toString());
        return this.http.get<CustomResponse>(`${this.apiUrl}/user/list/${limit}`)
          .pipe(
            tap(console.log),
            catchError(this.handleError)
          );
      }

    // save$ observable to subsribe to save user
    save$=(user:User)=><Observable<CustomResponse>>
    this.http.post<CustomResponse>(`${this.apiUrl}/user/register`,user)
    .pipe(
        tap(console.log),
        catchError(this.handleError)
    );
    //delete$ observable to subscribe to delete a user
    delete$=(userid:number)=> <Observable<CustomResponse>>
    this.http.delete<CustomResponse>('{this.apiUrl}/user/delete/${userid}')
    .pipe(
        tap(console.log),
        catchError(this.handleError)
    )
    //TBD create functionality to add update and get a single user
    //create a method to handle the error
    private handleError(error:HttpErrorResponse):Observable<never>{
        console.log(error);
        return throwError(`An error has occured-Error code : ${error.status}`)
    }





}