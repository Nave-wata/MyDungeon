package com.example.mainproject.asynchronous;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.mainproject.asynchronous.usersinfo.UsersInfo;
import com.example.mainproject.asynchronous.usersinfo.UsersInfoDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InitializeDatabase implements Runnable {
    private final AppDatabase db;
    private final Activity activity;

    public InitializeDatabase(AppDatabase db, Activity activity) {
        this.db = db;
        this.activity = activity;
    }

    @Override
    public void run() {
        doInBackground();
        //handler.post(() -> onPostExecute());
    }

    public void execute() {
        // onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new InitializeDatabase(db, activity));
    }

    // void onPreExecute() {}

    void doInBackground() {
        String NEXT_INFO = "NextInfo";
        String DS_Flag = "Flag";
        String DS_Name = "Name";
        String DS_Passwd = "Password";
        String name = "rLiemW3ABThcnBE4Sfkminm5XQpahw5nJKys6WgRjfj9puSKS8epfCGgKJ6yWTSkQfT3mVWAZcSZWHh9dxn7gz9pbB2HFypRbgtgjTUM6uSWMA3NQxsLYEfgaNgmhsx4";
        String password = "Es3tULhYfVfmmutz4GUBRayjKup5rR55DezEty7ce2Yp9AHmruPSCu8fa9jC5C5RgE3eZUizhiUz2K3Edpxszur6khkQhxeMTpVUtgTLf2gy2iDS4HWkWkTEi7Nk22mkLU3iG6xwVMdg8XKxA9dAKHy7wGrA3QQV7n5hub98KUUk6EhDTEjEJJwZEeaTGktJPY2ZT58pAxnXUpx5eMMPjHntksprMiGQj3P8TDwjyhaQs6mAVbU97557MzD9GhS2suUbfJS7hajtUMdumVefwMmYVVAuBwZMjrVJ6mnbZyufrZh7Puf6AaTWbZF88pm6fKiJcHnkVtcciXi6M4s6Zi2wnQ5BXFUpK2cQiu52JtniCiQ6gcDEQP7R8bVJSXeicg8WcsRyg72zjZWDUTANNTxiGZkbHWtwecYnufQFmkSHQJDayZ5b972bXtrLTeMkidMBsfSXHLGuYkiwZ23w5sfMkA3ASADR3SPrtHEEU86Px4fcdj9QPMd2t9SkVQWbVdsRAVD6Rrdj5j8wyJCJbSVdErUi8rGxErpnuYPMVP5QpNh9mmtbVNfhE6pptkxzXtFKQekaG5f7pQtGX7PceHHcRQcVgPTgTUPuSi4LmfURfZQVKfQnrfx2JrxyQkbsGV8Fk5N8QsHJgkNuyyz5AbEUShaP5zfh6pUxVZKfm46w5n5QrFutiBbUfB92Ht5Xp4LfHxkGZ6LDe9jaA8YiKttJ5KNueeK8FRZMfLs7tS6CrFiYAtxHdQH7JB9aEQznLr2yPiXHSP7PZMunSLEiBarirVWVLKzSKNQi5iHRWLcfZb75AKLE6mgf6BUScM7xWVwbWBKK2asLJQN9p3QVJ8EGJ4ypr3RFhKNmZbZjzYFZAJFsc3FVhKKSWBgsUJJftXKb2Nr2hj8YxQRTfQfs3msDiKagEgumhpj6YL9RjTZnXDBgkSEFNMCJBzNCNjcrU5eEhSrrh9eu6zJXKKTbJr9sR4RfwZdzeCDzuGDgQdAHzbLaHtxXJ5tNzzWW3W5Q";
        String salt = "pQvCtOmRBBq3bzYJayPmy2yXgsxdpKmq5ndVombb6GVXGVoRA0qIltbYhxK5h167dbryQ8DdALjnkGYzDYLaapEpLPRvtrX7l2OX9WshYfBDRgHi0dLtyJ7lYwA4TALAvWre6MibbaIKjMfyIYH37mMBllyHafdg13NSR23m9BqkZHvxgPgvpsr1naeJTgylOINJeOzd4pvLQLacQ0FntZc2eMheYF4BMT5M8BrRY38HXjIrZQa8WtqoOY3xlTLDxKKiia9YdQxDiACwmwOsGX2evxn8QsYAlyYfgIrY0mQZQzFYR4iHYm0lmyvBoLnVhthNEfVpb5kaPPM4SygFPX3Jq8x4oaAwIEPmxcXESlJ5hOQSWEKyE5HmqzNErgi112WzjnKLmyWOOfWVtfRjYhsaCLGmcyztyIZKv5KGXkTxFQIlWzL4IqlrYfI5zfn4wQvvardCjKHK2gbL5kYRnnCetNH08hqm73sBeacHWJ3CwmysxesHMBPeQzp2KJaww3T5ikvVYpWN0ygTFJ1NNkWKlleKylcZcwJHByaf7AqgEBYqAZC2zJW1Z6dm5lHi3aSrfr42ZnYzcxmhy5SMLsxIQMq4Fzk6M6ytEaJJz4rexO0HtzyMfp9KKolDaOeFItSiimyJZrqsqSRyJbYyTL8i8gLdMiolBkH0JhTyx2v3BLWTkNyNHdk4bAJkkB6rXHI4KdfgTTID06546Es98wKSdgQgyISloWL4T5v4EvR4pkJQnVjbLfAnkTNDYmrQmqsYclrk6Wd874bVKmSH3Nv6d689ly1J8iYa99WDchkt3Y1Eaj1exF2Oq7fHyyYEWG9HSJFdRjhO2wMZfYVG8XqYCwJGoyvbqYSxH2TEsp2mmkYJRSzvIcyjhhOwyGOcmtC2OCHIyK3yyATYWylO2gdyB7cxdXjrCfgegLeAXIhJcHJOyEaYMIAGx03ZOpenihdqdWndYA";
        String hash = "bbaab420984ac79939d2abbd6ea788d5226e2b5dfa25df48ec165708f75d7e7296d0ae3fb2e806068ec76f5b49393778a86131bddc2b503cea52d0d029ed4f65";
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        SharedPreferences dataStore = activity.getSharedPreferences(NEXT_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = dataStore.edit();

        editor.putBoolean(DS_Flag, true);
        editor.putString(DS_Name, name);
        editor.putString(DS_Passwd, password);
        editor.apply();

        try {
            usersInfoDao.insert(new UsersInfo(name, salt, hash));
        } catch (Exception ignored) {
        }
    }

    //void onPostExecute() {}
}