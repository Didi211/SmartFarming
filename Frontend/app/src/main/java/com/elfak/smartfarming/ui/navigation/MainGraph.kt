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
                    navController.navigate(Screen.GraphScreen.withArgs(sensorId))
                }
            )
        }
        composable(Screen.GraphScreen.route + "/{sensorId}") {
            val viewModel = hiltViewModel<GraphScreenViewModel>()
            GraphScreen(
                viewModel = viewModel,
                navigateToDeviceDetails = { deviceId ->
                    navController.navigate(Screen.DeviceDetailsScreen.withArgs(deviceId))
                },
                navigateToRuleDetails = { ruleId ->
                    navController.navigate(Screen.RuleDetailsScreen.withArgs(ruleId))
                }
            )
        }
        composable(Screen.ListScreen.route) {
            val viewModel = hiltViewModel<ListScreenViewModel>()
            ListScreen(
                viewModel = viewModel,
                 navigateToDeviceDetails = { deviceId ->
                     navController.navigate(Screen.DeviceDetailsScreen.withArgs(deviceId))
                 },
                 navigateToRuleDetails = { ruleId ->
                     navController.navigate(Screen.RuleDetailsScreen.withArgs(ruleId))
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
                    navController.navigate(Screen.RuleDetailsScreen.withArgs(ruleId)) {
                        popUpTo(Screen.HomeScreen.route)
                    }
                }
            )
        }
        composable(Screen.DeviceDetailsScreen.route + "/{deviceId}") {
            val viewModel = hiltViewModel<DeviceDetailsScreenViewModel>()
            DeviceDetailsScreen(
                viewModel = viewModel,
                navigateToRuleDetails = { ruleId ->
                    navController.navigate(Screen.RuleDetailsScreen.withArgs(ruleId)) {
                        launchSingleTop = true
                        popUpTo(Screen.DeviceDetailsScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.RuleDetailsScreen.route + "/{ruleId}") {
            val viewModel = hiltViewModel<RuleDetailsScreenViewModel>()
            RuleDetailsScreen(
                viewModel = viewModel,
                navigateToDeviceDetails = { deviceId ->
                    navController.navigate(Screen.DeviceDetailsScreen.withArgs(deviceId)) {
                        launchSingleTop = true
                        popUpTo(Screen.RuleDetailsScreen.route) {
                            inclusive = true
                        }
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