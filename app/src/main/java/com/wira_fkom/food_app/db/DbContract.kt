package com.wira_fkom.food_app.db

object DbContract {

    var ip = "192.168.205.187"

    val urlRegister = "http://$ip/server-api-food-app//api-register.php"
    val urlLogin = "http://$ip/server-api-food-app//api-login.php"
    val urlProfile = "http://$ip/server-api-food-app//read_user_profile.php"
    val update_user_profile = "http://$ip/server-api-food-app//read_user_profile.php"
}