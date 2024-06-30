package com.wira_fkom.food_app.db

object DbContract {

    var ip = "192.168.199.187"
//    var ip = "192.168.0.229"

    val urlRegister = "http://$ip/server-api-food-app//api-register.php"
    val urlLogin = "http://$ip/server-api-food-app//api-login.php"
    val urlForgotPassword = "http://$ip/server-api-food-app//api-forgot-password.php"

}