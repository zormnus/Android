package ru.mirea.zotovml.mireaproject.roomFiles;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "accounts")
public class PersonDBEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "Email")
    private String email;
    @ColumnInfo(name = "Password")
    private String password;
    @ColumnInfo(name = "CarBrand")
    private String brandOfCar;
    @ColumnInfo(name = "CarType")
    private String typeOfCar;
    @ColumnInfo(name = "RegNumber")
    private String regNumber;

    public PersonDBEntity(Long id,String email, String password, String brandOfCar
            ,String typeOfCar,String regNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.brandOfCar = brandOfCar;
        this.typeOfCar = typeOfCar;
        this.regNumber = regNumber;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getBrandOfCar() {
        return brandOfCar;
    }

    public String getPassword() {
        return password;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getTypeOfCar() {
        return typeOfCar;
    }

    public void setBrandOfCar(String brandOfCar) {
        this.brandOfCar = brandOfCar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public void setTypeOfCar(String typeOfCar) {
        this.typeOfCar = typeOfCar;
    }
}
