package com.example.kel_10_feed.model

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList
import java.io.Serializable

class OrderDetails():Serializable {
    var userid: String? =null
    var username:String?=null
    var foodNames:MutableList<String>?=null
    var foodImages:MutableList<String>?=null
    var foodPrices:MutableList<String>?=null
    var foodQuantities:MutableList<Int>?=null
    var address:String?=null
    var totalPrice:String?=null
    var phoneNumber:String?=null
    var orderAceepted:Boolean =false
    var paymentReceived:Boolean=false
    var itemPushKey:String?=null
    var currentTime: Long=0

    constructor(parcel: Parcel) : this() {
        userid = parcel.readString()
        username = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAceepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    constructor(
        userId: String,
        name: String,
        foodItemName: ArrayList<String>,
        foodItemPrice: ArrayList<String>,
        foodItemImage: ArrayList<String>,
        foodItemQuantities: ArrayList<Int>,
        address: String,
        totalAmount:String,
        phone: String,
        time: Long,
        itemPushKey: String?,
        b: Boolean,
        b1: Boolean
    ) : this(){
        this.userid= userId
        this.username= name
        this.foodNames=foodItemName
        this.foodPrices=foodItemPrice
        this.foodImages=foodItemImage
        this.foodQuantities=foodItemQuantities
        this.address=address
        this.totalPrice= totalAmount
        this.phoneNumber=phone
        this.currentTime=time
        this.itemPushKey= itemPushKey
        this.orderAceepted= orderAceepted
        this.paymentReceived=paymentReceived
    }


   fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userid)
        parcel.writeString(username)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (orderAceepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

    fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}