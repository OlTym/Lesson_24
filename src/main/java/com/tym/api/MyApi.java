package com.tym.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tym.Dao.StorageDao;
import com.tym.Dao.UserDao;
import com.tym.Model.User;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.DELETE;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/*
Создать API для работы с классом User (int id, String name, int age):
        POST: создать нового юзера. Передать на сервер в формате x-www-form-urlencoded name и age. Сервер должен вернуть JSON с созданным юзером у которого присвоен id.
        PUT: изменить юзера по id
        DELETE: удалить
        GET: получить юзера по id
        GET: получить всех юзеров
        Каждый метод должен возвращать код возврата и ответ в формате JSON
        с обьянением результата. Непример если DELETE запрос прошел успешно,
        то код возврата будет 200 и соотв. сообщение а если была попытка удалить юзера
        которого нет, то вернуть код 400 и сооттв. текст.
*/

@Path("/")
public class MyApi {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        Response.Status status;
        StorageDao dao = new UserDao();
        List<User> list = dao.getAllUsers();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result;
        if (list.size() != 0) {
            result = gson.toJson(list);
            status = Response.Status.OK;

        } else {
            status = Response.Status.BAD_REQUEST;
            result = gson.toJson("The base is empty");

        }
        return Response.status(status).entity(result).build();

    }

    @GET
    @Path("/user")
    public Response getUser(@QueryParam("id") String id) {
        StorageDao dao = new UserDao();
        String result;
        Response.Status status;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = dao.getUser(Integer.decode(id));

        if (user != null) {
            result = gson.toJson(user);
            status = Response.Status.OK;
        } else {
            result = gson.toJson("This user is not in the base");
            status = Response.Status.BAD_REQUEST;
        }
        return Response.status(status).entity(result).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@FormParam("name") String name, @FormParam("age") int age) {
        User user = new User(name, age);
        StorageDao dao = new UserDao();
        dao.insertUser(user);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(user);
        return Response.status(Response.Status.OK).entity(result).build();
    }


    @DELETE
    @Path("/deleteID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserbyID(@FormParam("id") int id) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Response.Status status;
        String message;
        StorageDao dao = new UserDao();
        User user = dao.getUser(id);
        if (user != null) {

            message = "User id=" + id + " is deleted from the base";
            status = Response.Status.OK;
            dao.removeUser(user);
        } else {
            message = "User id=" + id + " is not in the base";
            status = Response.Status.BAD_REQUEST;

        }
        return Response.status(status).entity(gson.toJson(message)).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserbyName(@FormParam("name") String name) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Response.Status status;
        String message;
        StorageDao dao = new UserDao();
        User user = dao.getUserByName(name);
        if (user != null) {
            message = "User " + user.getName() + " is deleted from the base";
            status = Response.Status.OK;
            dao.removeUser(user);
        } else {
            message = "User " + name + " is not in the base";
            status = Response.Status.BAD_REQUEST;
        }
        return Response.status(status).entity(gson.toJson(message)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response putUser(@FormParam("id") int id, @FormParam("age") int age) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Response.Status status;
        String message;
        StorageDao dao = new UserDao();
        User user = dao.getUser(id);
        if (user != null) {
            user.setAge(age);
            dao.updateUser(user);
            message = "User id=" + id + " is updated from the base";
            status = Response.Status.OK;
        } else {
            message = "User id=" + id + " is not in the base";
            status = Response.Status.BAD_REQUEST;
        }
        return Response.status(status).entity(gson.toJson(message)).build();
    }
}
