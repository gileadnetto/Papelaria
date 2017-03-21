package com.example.gilead.tccpatrimonio.service;

import com.example.gilead.tccpatrimonio.Estoque;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Gilead on 20/12/2016.
 */

public class EstoqueDec implements JsonDeserializer{
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement element = json.getAsJsonObject();

        if(json.getAsJsonObject() !=null){
            element = json.getAsJsonObject();

        }

        return (new Gson().fromJson(element,Estoque.class));
    }
}
