package com.example.fitbox;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(
        tableName = "user_account"
)
public class UserAccount {
    @PrimaryKey(autoGenerate = true) private long userId;
    @ColumnInfo(name = "name") private String name;
    @ColumnInfo(name = "surname") private String surname;
    @ColumnInfo(name = "password") private String password;
    @ColumnInfo(name = "email")private String email;

    public UserAccount(String name, String surname, String password, String email) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }


    // Add a method to retrieve the userId from the database
    public long retrieveUserIdFromDatabase(UserAccountDao userAccountDao) {
        return userAccountDao.getLastInsertedUserId();
        //setUserId(userId);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String name) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

