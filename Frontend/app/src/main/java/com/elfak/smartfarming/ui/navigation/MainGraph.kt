package com.elfak.smartfarming.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.elfak.smartfarming.ui.screens.addRuleScreen.AddRuleScreen
import com.elfak.smartfarming.ui.screens.addRuleScreen.AddRuleScreenViewModel
import com.elfak.smartfarming.ui.screens.deviceDetailsScreen.DeviceDetailsScreen
import com.elfak.smartfarming.ui.screens.deviceDetailsScreen.DeviceDetailsScreenViewModel
import com.elfak.smartfarming.ui.screens.graphScreen.GraphScreen
import com.elfak.smartfarming.ui.screens.graphScreen.GraphScreenViewModel
import com.elfak.smartfarming.ui.screens.homeScreen.HomeScreen
import com.elfak.smartfarming.ui.screens.homeScreen.HomeScreenViewModel
import com.elfak.smartfarming.ui.screens.listScreen.ListScreen
import com.elfak.smartfarming.ui.screens.listScreen.ListScreenViewModel
import com.elfak.smartfarming.ui.screens.notificationScreen.NotificationScreen
import com.elfak.smartfarming.ui.screens.notificationScreen.NotificationScreenViewModel
import com.elfak.smartfarming.ui.screens.ruleDetailsScreen.RuleDetailsScreen
import com.elfak.smartfarming.ui.screens.ruleDetailsScreen.RuleDetailsScreenViewModel
import com.elfak.smartfarming.ui.screens.settingsScreen.SettingsScreen
import com.elfak.smartfarming.ui.screens.settingsScreen.SettingsScreenViewModel

fun NavGraphBuilder.mainGraph(navController: NavController) {

    navigation(startDestination = Screen.HomeScreen.route, route = Screen.Main.route) {
        composable(Screen.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(
                viewModel = viewModel,
                navigateToGraphReadings = { sensorId ->
                    navController.navigate(Screen.GraphScreen.withOptionalArgs(
                        NavigationArgument("sensorId",sensorId)
                    ))
                }
            )
        }
        composable(Screen.GraphScreen.route) {
            val viewModel = hiltViewModel<GraphScreenViewModel>()
            GraphScreen(
                viewModel = viewModel,
                navigateToDeviceDetails = { deviceId ->
                    navController.navigate(Screen.DeviceDetailsScreen.withOptionalArgs(
                        NavigationArgument("deviceId", deviceId)
                    )) {
                        popUpTo(Screen.Main.route)
                    }
                },
                navigateToRuleDetails = { ruleId ->
                    navController.navigate(Screen.RuleDetailsScreen.withOptionalArgs(
                        NavigationArgument("ruleId", ruleId)
                    )) {
                        popUpTo(Screen.Main.route)
                    }
                }
            )
        }
        composable(Screen.ListScreen.route) {
            val viewModel = hiltViewModel<ListScreenViewModel>()
            ListScreen(
                viewModel = viewModel,
                 navigateToDeviceDetails = { deviceId ->
                     navController.navigate(Screen.DeviceDetailsScreen.withOptionalArgs(
                         NavigationArgument("deviceId", deviceId)
                     )) {
                         popUpTo(Screen.Main.route)
                     }
                 },
                 navigateToRuleDetails = { ruleId ->
                     navController.navigate(Screen.RuleDetailsScreen.withOptionalArgs(
                         NavigationArgument("ruleId", ruleId)
                     )) {
                         popUpTo(Screen.Main.route)
                     }
                 }
            )
        }
        composable(Screen.SettingScreen.route) {
            val viewModel = hiltViewModel<SettingsScreenViewModel>()
            SettingsScreen(viewModel = viewModel)
        }
        composable(Screen.NotificationScreen.route) {
            val viewModel = hiltViewModel<NotificationScreenViewModel>()
            NotificationScreen(viewModel = viewModel)
        }
        composable(Screen.AddRuleScreen.route) {
            val viewModel = hiltViewModel<AddRuleScreenViewModel>()
            AddRuleScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() },
                navigateToRuleDetails = { ruleId ->
                    navController.navigate(Screen.RuleDetailsScreen.withOptionalArgs(
                        NavigationArgument("ruleId", ruleId)
                    )) {
                        popUpTo(Screen.Main.route)
                    }
                }
            )
        }
        composable(Screen.DeviceDetailsScreen.route) {
            val viewModel = hiltViewModel<DeviceDetailsScreenViewModel>()
            DeviceDetailsScreen(
                viewModel = viewModel,
                navigateToRuleDetails = { ruleId ->
                    navController.navigate(Screen.RuleDetailsScreen.withOptionalArgs(
                        NavigationArgument("ruleId", ruleId)
                    )) {
//                        launchSingleTop = true
//                        popUpTo(Screen.Main.route)
                    }
                }
            )
        }
        composable(Screen.RuleDetailsScreen.route) {
            val viewModel = hiltViewModel<RuleDetailsScreenViewModel>()
            RuleDetailsScreen(
                viewModel = viewModel,
                navigateToDeviceDetails = { deviceId ->
                    navController.navigate(Screen.DeviceDetailsScreen.withOptionalArgs(
                        NavigationArgument("deviceId", deviceId)
                    )) {
//                        launchSingleTop = true
//                        popUpTo(Screen.Main.route)
                    }
                }
            )
        }
    }
}

private fun onAuthenticationFailed(navController: NavController) {
    navController.navigate(Screen.WelcomeScreen.route) {
        popUpTo(Screen.Main.route) { inclusive = true }
    }
}