package com.santiago.priotti_api.User;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserPresenter {

    public String fullTable(List<User> userList){
        List<List<String>> datatablesUserList;
        datatablesUserList = userList.stream().map(user -> Arrays.asList(
                user.getId().toString(),
                user.getName(),
                user.getNumber(),
                user.getCuit(),
                user.getEmail(),
                user.getCoeficient().toString(),
                user.getLastLogin(),
                user.getVisits().toString(),
                user.getStatus()))
                .collect(Collectors.toList());
        return new Gson().toJson(datatablesUserList);
    }


}
