package com.elfak.smartfarming.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cable
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elfak.smartfarming.data.models.MenuItem
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.ui.components.scaffold.SmartFarmingBottomAppBar
import com.elfak.smartfarming.ui.components.scaffold.SmartFarmingTopAppBar
import com.elfak.smartfarming.ui.screens.loginScreen.LoginScreen
import com.elfak.smartfarming.ui.screens.loginScreen.LoginScreenViewModel
import com.elfak.smartfarming.ui.screens.registerScreen.RegisterScreen
import com.elfak.smartfarming.ui.screens.registerScreen.RegisterScreenViewModel
import com.elfak.smartfarming.ui.screens.splashScreen.SplashScreen
import com.elfak.smartfarming.ui.screens.splashScreen.SplashScreenViewModel
import com.elfak.smartfarming.ui.screens.welcomeScreen.WelcomeScreen
import com.elfak.smartfarming.ui.theme.BackgroundVariant
import com.elfak.smartfarming.ui.theme.FontColor
import kotlinx.coroutines.launch


@Composable
fun Navigation() {
    val viewModel = hiltViewModel<NavigationViewModel>()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = Screen.fromRoute(currentDestination?.route ?: "")

    val showScaffold = currentDestination?.parent?.route == Screen.Main.route
    if (showScaffold) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val items = prepareMenuList(navController, viewModel::signOut)
        val selectedItem = remember { mutableStateOf(items[0]) }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(12.dp))
                    items.forEach { item ->
                        NavigationDrawerItem(
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondary,
                                selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                                selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                                unselectedTextColor = FontColor,
                                unselectedIconColor = FontColor,
                            ),
                            icon = { Icon(item.icon, contentDescription = item.name) },
                            label = { Text(item.name) },
//                            selected = item.name == selectedItem.value.name,
                            selected = item.name == currentScreen?.displayName,
                            onClick = {
                                item.action()
                                scope.launch { drawerState.close() }
                                selectedItem.value = item
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            },
            content = {
                Scaffold(
                    containerColor = BackgroundVariant,
                    topBar = {
                        AnimatedVisibility(visible = currentDestination?.parent?.route == Screen.Main.route) {
                            SmartFarmingTopAppBar(title = currentScreen?.displayName ?: "ERROR") {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        }
                    },
                    bottomBar = {
                        val screens = listOf(Screen.HomeScreen.route, Screen.ListScreen.route)
                        val showBottomBar = screens.contains(currentScreen?.route)
                        AnimatedVisibility(visible = showBottomBar) {
                            SmartFarmingBottomAppBar(buttons = prepareBottomBarButtons(navController))
                        }
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.SplashScreen.route) {
                            val splashScreenViewModel = hiltViewModel<SplashScreenViewModel>()
                            SplashScreen(
                                viewModel = splashScreenViewModel,
                                navigateToHome = {
                                    navController.navigate(Screen.Main.route) {
                                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                                    }
                                },
                                navigateToWelcome = {
                                    navController.navigate(Screen.WelcomeScreen.route) {
                                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.WelcomeScreen.route) {
                            WelcomeScreen(
                                navigateToLogin = { navController.navigate(Screen.LoginScreen.route) },
                                navigateToRegister = { navController.navigate(Screen.RegisterScreen.route) }
                            )
                        }
                        composable(Screen.LoginScreen.route) {
                            val loginViewModel = hiltViewModel<LoginScreenViewModel>()
                            LoginScreen(
                                viewModel = loginViewModel,
                                navigateBack = { navController.popBackStack() },
                                navigateToHome = {
                                    navController.navigate(Screen.Main.route) {
                                        // removes all from backstack so when user clicks back button it will close the app
                                        popUpTo(Screen.WelcomeScreen.route) { inclusive = true }
                                    }
                                },
                            )
                        }
                        composable(Screen.RegisterScreen.route){
                            val registerViewModel = hiltViewModel<RegisterScreenViewModel>()
                            RegisterScreen(
                                viewModel = registerViewModel,
                                navigateBack = { navController.popBackStack() },
                                navigateToHome = {
                                    navController.navigate(Screen.Main.route){
                                        popUpTo(Screen.WelcomeScreen.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        mainGraph(navController = navController)
                    }
                }
            }
        )

    }
    else {
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
        ) {
            composable(Screen.SplashScreen.route) {
                val splashScreenViewModel = hiltViewModel<SplashScreenViewModel>()
                SplashScreen(
                    viewModel = splashScreenViewModel,
                    navigateToHome = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.SplashScreen.route) { inclusive = true }
                        }
                    },
                    navigateToWelcome = {
                        navController.navigate(Screen.WelcomeScreen.route) {
                            popUpTo(Screen.SplashScreen.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.WelcomeScreen.route) {
                WelcomeScreen(
                    navigateToLogin = { navController.navigate(Screen.LoginScreen.route) },
                    navigateToRegister = { navController.navigate(Screen.RegisterScreen.route) }
                )
            }
            composable(Screen.LoginScreen.route) {
                val loginViewModel = hiltViewModel<LoginScreenViewModel>()
                LoginScreen(
                    viewModel = loginViewModel,
                    navigateBack = { navController.popBackStack() },
                    navigateToHome = {
                        navController.navigate(Screen.Main.route) {
                            // removes all from backstack so when user clicks back button it will close the app
                            popUpTo(Screen.WelcomeScreen.route) { inclusive = true }
                        }
                    },
                )
            }
            composable(Screen.RegisterScreen.route){
                val registerViewModel = hiltViewModel<RegisterScreenViewModel>()
                RegisterScreen(
                    viewModel = registerViewModel,
                    navigateBack = { navController.popBackStack() },
                    navigateToHome = {
                        navController.navigate(Screen.Main.route){
                            popUpTo(Screen.WelcomeScreen.route) { inclusive = true }
                        }
                    }
                )
            }
            mainGraph(navController = navController)
        }
    }

}

fun prepareMenuList(navController: NavController, signOutAction: () -> Unit): List<MenuItem> {
    val items = listOf(
        MenuItem(
            name = Screen.HomeScreen.displayName,
            icon = Icons.Rounded.Home,
            action = {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.HomeScreen.route) { inclusive = true }
                }
            }
        ),
        MenuItem(
            name = Screen.ListScreen.displayName,
            icon = Icons.Rounded.Sensors,
            action = {
                navController.navigate(Screen.ListScreen.route) {
                    popUpTo(Screen.HomeScreen.route)
                }
            }
        ),
        MenuItem(
            name = Screen.NotificationScreen.displayName,
            icon = Icons.Rounded.Home,
            action = {
                navController.navigate(Screen.NotificationScreen.route) {
                    popUpTo(Screen.HomeScreen.route)
                }
            }
        ),
        MenuItem(
            name = Screen.SettingScreen.displayName,
            icon = Icons.Rounded.Settings,
            action = {
                navController.navigate(Screen.SettingScreen.route) {
                    popUpTo(Screen.HomeScreen.route)
                }
            }
        ),
        MenuItem(
            name = "Sign out",
            icon = Icons.Rounded.ExitToApp,
            action = {
                signOutAction()
                navController.navigate(Screen.WelcomeScreen.route) {
                    popUpTo(Screen.Main.route) { inclusive = true }
                }
            }
        ),
    )
    return items
}

fun prepareBottomBarButtons(navController: NavController): List<MenuItem> {
    return listOf(
        MenuItem(
            name = "Add device",
            icon = Icons.Rounded.Sensors,
            action = {
                navController.navigate(Screen.DeviceDetailsScreen
                    .withArgs(ScreenState.Create.name))
            }
        ),
        MenuItem(
            name = "Add rule",
            icon = Icons.Rounded.Cable,
            action = {
                navController.navigate(Screen.RuleDetailsScreen
                    .withArgs(ScreenState.Create.name))
            }
        )
    )
}

