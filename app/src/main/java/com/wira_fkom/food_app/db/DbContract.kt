package com.wira_fkom.food_app.db

object DbContract {

    var ip = "192.168.205.187"
//    var ip = "192.168.0.229"

    val urlRegister = "http://$ip/server-api-food-app//api-register.php"
    val urlLogin = "http://$ip/server-api-food-app//api-login.php"
    val create_user_profile = "http://$ip/server-api-food-app//create.php"
    val read_user_profile = "http://$ip/server-api-food-app//read_user_profile.php"
    val update_user_profile = "http://$ip/server-api-food-app//read_user_profile.php"
}