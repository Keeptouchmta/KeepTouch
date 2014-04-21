package com.keeptouch.location;

import android.graphics.drawable.Drawable;

/**
 * Created by tgoldberg on 4/18/2014.
 */
public class Profile {

    int m_UserId;
    String m_FirstName;
    String m_LastName;
    Boolean m_IsMale;
    String m_Email;
    String m_FacebookAccount;
    Boolean m_IsContact;
    Drawable m_ProfilePicture;

    String m_LastMessageText;
    int unreadCount=0;


    /**
     * Constructor - get the contact user id , user email
     */
    public Profile(int i_UserId,String i_Email)
    {
        m_UserId = i_UserId;
        m_Email = i_Email;
    }

    /**
     * Constructor - gets the contact user id
     */
    public Profile(int i_UserId){
        m_UserId = i_UserId;
    }

    /**
     * Set this user details
     */
    public void setDetails(String i_FirstName, String i_LastName,
                          Boolean i_IsMale,String i_Email, String i_FacebookAccount)
    {
        m_FirstName = i_FirstName;
        m_LastName = i_LastName;
        m_IsMale = i_IsMale;
        m_Email = i_Email;
        m_FacebookAccount = i_FacebookAccount;
    }

    /**
     * @return  userid
     */
    public int GetUserId()
    {
        return m_UserId;
    }


    /**
     *@return user first name
     */
    public String getFirstName()
    {
        return m_FirstName;
    }

    /**
     * @return user last name
     */
    public String getLastName(){
        return m_LastName;
    }

    /**
     * @return this user gender
     */
    public Boolean isMale() {
        return m_IsMale;
    }

    /**
     * @return this user email
     */
    public String getEmail(){
        return m_Email;
    }

    /**
     * @return this user facebook accound id
     */
    public String getFacebookAccount(){
        return m_FacebookAccount;
    }


    /**
     * Set this contact last message text
     * To be used in the contacts activity
     */
    public void setLastMessageText(String i_MessageText) {
        m_LastMessageText = i_MessageText;
    }

    /**
     * @return this contact last message text
     * Should be used in the contacts activity
     */
    public String getLastMessageText(){
        return m_LastMessageText;
    }

    /**
     * Increment the number of messages from that contact that still was not read
     */
    public void incrementUnreadCount() {
        unreadCount++;
    }

    /**
     * @return the number of message from that contact that still was not read
     */
    public int getUnreadCount(){
        return unreadCount;
    }

    /**
     * Clear the unread messages count
     */
    public void clearUnreadCount() {
        unreadCount=0;
    }

    /**
     * Set this user first name
     */
    public void setFirstName(String i_FirstName) {
        m_FirstName = i_FirstName;
    }

    /**
     * Set this user last name
     */
    public void setLastName(String i_LastName) {
        m_LastName = i_LastName;
    }

    /**
     * Set this user gender
     */
    public void setIsMale(Boolean i_IsMale) {
        m_IsMale = i_IsMale;
    }

    /**
     * Set this user email
     */
    public void setEmail(String i_Email) {
        m_Email = i_Email;
    }

    /**
     * Set this user facebook accound id
     */
    public void setFacebookAccount(String i_FacebookAccount) {
        m_FacebookAccount = i_FacebookAccount;
    }
}
