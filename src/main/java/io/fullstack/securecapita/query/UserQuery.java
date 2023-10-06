package io.fullstack.securecapita.query;

public class UserQuery {
    public static final String INSERT_USER_QUERY = "INSERT INTO Users(first_name,last_name,email,password) VALUES (:firstName,:lastName,:email,:password)";
   // public static final String COUNT_USER_EMAIL_QUERY = "SELECT COUNT * FROM Users WHERE email=:email";
    public static final String COUNT_USER_EMAIL_QUERY = "SELECT COUNT(*) FROM Users WHERE email = :email";

    public static final String INSERT_ACCOUNT_VERIFICATION_URL_QUERY = "INSERT INTO AccountVerifications (user_id,url) VALUES (:userId,:url)";

    public static final String SELECT_USER_BY_USERID_QUERY="SELECT * FROM Users where id= :userId";
    public static final String UPDATE_USER_QUERY="UPDATE Users SET (first_name=:firstName,last_name=:lastName,email=:email,password=:password) WHERE (id =:userId) ";

    public static final String DELETE_USER_BY_USERID_QUERY="DELETE FROM Users WHERE (id=:userId)";

    public static final  String SELECT_ALL_USERS_QUERY="SELECT * FROM Users Limit :Limit ";
}
