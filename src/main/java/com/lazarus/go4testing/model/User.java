package com.lazarus.go4testing.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String password;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
