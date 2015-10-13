package com.codeattitude.mainframeautorequest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xaviermaximin on 13/10/2015.
 */
public class IndexSummary {


    @SerializedName("unseen_todo_conversations")
    Integer unseenTodoConversations;
    @SerializedName("unseen_conversations")
    Integer unseenConversations;
    @SerializedName("total_todo_conversations")
    Integer totalTodoConversations;
    @SerializedName("total_requested_actions")
    Integer totalRequestedActions;

    @SerializedName("total_conversations")

    Integer totalConversations;

    @SerializedName("todo_index")
    String todoIndex;
    @SerializedName("all_index")
    String allIndex;


    public IndexSummary(){}

    public Integer getUnseenTodoConversations() {
        return unseenTodoConversations;
    }

    public void setUnseenTodoConversations(Integer unseenTodoConversations) {
        this.unseenTodoConversations = unseenTodoConversations;
    }

    public Integer getUnseenConversations() {
        return unseenConversations;
    }

    public void setUnseenConversations(Integer unseenConversations) {
        this.unseenConversations = unseenConversations;
    }

    public Integer getTotalRequestedActions() {
        return totalRequestedActions;
    }

    public void setTotalRequestedActions(Integer totalRequestedActions) {
        this.totalRequestedActions = totalRequestedActions;
    }

    public Integer getTotalConversations() {
        return totalConversations;
    }

    public void setTotalConversations(Integer totalConversations) {
        this.totalConversations = totalConversations;
    }

    public String getTodoIndex() {
        return todoIndex;
    }

    public void setTodoIndex(String todoIndex) {
        this.todoIndex = todoIndex;
    }

    public String getAllIndex() {
        return allIndex;
    }

    public void setAllIndex(String allIndex) {
        this.allIndex = allIndex;
    }


    public Integer getTotalTodoConversations() {
        return totalTodoConversations;
    }

    public void setTotalTodoConversations(Integer totalTodoConversations) {
        this.totalTodoConversations = totalTodoConversations;
    }

}
