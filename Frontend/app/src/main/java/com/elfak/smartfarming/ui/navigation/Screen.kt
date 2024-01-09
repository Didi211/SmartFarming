package com.elfak.smartfarming.ui.navigation

sealed class Screen(val route: String, val displayName: String = "")  {
    //auth graph
    data object SplashScreen: Screen("splash_screen", "Splash")
    data object WelcomeScreen: Screen("welcome_screen", "Welcome")
    data object LoginScreen: Screen("login_screen", "Login")
    data object RegisterScreen: Screen("register_screen", "Register")

    //main graph
    data object Main: Screen("main", "Main")
    data object HomeScreen: Screen("home_screen", "Home")
    data object GraphScreen: Screen("graph_screen", "Graph readings")
    data object ListScreen: Screen("list_screen", "Devices and rules")
    data object SettingScreen: Screen("settings_screen", "Settings")
    data object NotificationScreen: Screen("notification_screen", "Notifications")
    data object AddRuleScreen: Screen("add_rule_screen", "Add rule")
    data object DeviceDetailsScreen: Screen("device_details_screen", "Device details")
    data object RuleDetailsScreen: Screen("rule_details_screen", "Rule details")


    companion object {
        private val routeMap = mapOf(
            SplashScreen.route to SplashScreen,
            WelcomeScreen.route to WelcomeScreen,
            LoginScreen.route to LoginScreen,
            RegisterScreen.route to RegisterScreen,
            Main.route to Main,
            HomeScreen.route to HomeScreen,
            GraphScreen.route to GraphScreen,
            ListScreen.route to ListScreen,
            SettingScreen.route to SettingScreen,
            NotificationScreen.route to NotificationScreen,
            AddRuleScreen.route to AddRuleScreen,
            DeviceDetailsScreen.route to DeviceDetailsScreen,
            RuleDetailsScreen.route to RuleDetailsScreen
        )

        fun fromRoute(route: String): Screen? {
            val screenName = route.split('/')[0]
            return routeMap[screenName]
        }
    }

    fun withOptionalArgs(vararg args: NavigationArgument): String {
        return buildString {
            append("$route?")
            args.forEach { arg ->
                append("${arg.key}=${arg.value}")
            }
        }
    }
    fun withArgs(vararg args: String): String  {
        return buildString {
            append("$route")
            args.forEach { arg ->
                append("/${arg}")
            }
        }
    }
}

data class NavigationArgument(
    val key: String,
    val value: String
)