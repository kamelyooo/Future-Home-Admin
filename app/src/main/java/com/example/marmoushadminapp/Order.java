
package com.example.marmoushadminapp;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

public class Order implements Serializable
{

  private String titleAr;
  private String stateAr;

  public String getTitleAr() {
    return titleAr;
  }

  public void setTitleAr(String titleAr) {
    this.titleAr = titleAr;
  }

  public String getStateAr() {
    return stateAr;
  }

  public void setStateAr(String stateAr) {
    this.stateAr = stateAr;
  }

  private Integer height;
  private Date created;
  private String state;
  private Integer code;
  private String ownerId;
  private String url;
  private String Note;
  private String objectId;
  private String title;
  private Integer width;
  private Date updated;
  public Integer getHeight()
  {
    return height;
  }

  public void setHeight( Integer height )
  {
    this.height = height;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getState()
  {
    return state;
  }

  public void setState( String state )
  {
    this.state = state;
  }

  public Integer getCode()
  {
    return code;
  }

  public void setCode( Integer code )
  {
    this.code = code;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl( String url )
  {
    this.url = url;
  }

  public String getNote()
  {
    return Note;
  }

  public void setNote( String Note )
  {
    this.Note = Note;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle( String title )
  {
    this.title = title;
  }

  public Integer getWidth()
  {
    return width;
  }

  public void setWidth( Integer width )
  {
    this.width = width;
  }

  public Date getUpdated()
  {
    return updated;
  }

                                                    
  public Order save()
  {
    return Backendless.Data.of( Order.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Order> callback )
  {
    Backendless.Data.of( Order.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Order.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Order.class ).remove( this, callback );
  }

  public static Order findById( String id )
  {
    return Backendless.Data.of( Order.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Order> callback )
  {
    Backendless.Data.of( Order.class ).findById( id, callback );
  }

  public static Order findFirst()
  {
    return Backendless.Data.of( Order.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Order> callback )
  {
    Backendless.Data.of( Order.class ).findFirst( callback );
  }

  public static Order findLast()
  {
    return Backendless.Data.of( Order.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Order> callback )
  {
    Backendless.Data.of( Order.class ).findLast( callback );
  }

  public static List<Order> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Order.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Order>> callback )
  {
    Backendless.Data.of( Order.class ).find( queryBuilder, callback );
  }
}