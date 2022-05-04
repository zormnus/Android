package ru.mirea.zotovml.httpurlconnection

class UserData(private val ip: String, private val city: String,
               private val country: String,private val region: String) {

    fun getCountry(): String {return country}
    fun getCity(): String {return city}
    fun getIp(): String {return ip}
    fun getRegion(): String {return city}
}