package com.example.bsuirjournal2.ui.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.bsuirjournal2.model.User
import com.example.bsuirjournal2.viewmodels.AuthorisationViewModel
import com.example.bsuirjournal2.viewmodels.GroupsViewModel

@Composable
fun AuthorisationScreen(
    authorisationViewModel: AuthorisationViewModel,
    navController: NavController,
    editor: SharedPreferences.Editor,
    modifier: Modifier
) {

    val username = remember{ mutableStateOf("") }
    val password = remember{ mutableStateOf("") }

    var isRegistartionButtonVisible by remember { mutableStateOf(true) }

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 150.dp)
    ) {
        Text(
            text = "Регистрация",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = "Логин")}
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Пароль")}
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            if (isRegistartionButtonVisible) {
                Button(
                    onClick = {
                        isRegistartionButtonVisible = false
                        authorisationViewModel.registerUser(
                            user = User(
                                id = 0,
                                username = username.value,
                                password = password.value
                            )
                        )
                        editor.apply {
                            putString("password", password.value)
                            commit()
                        }
                    },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Зарегестрироваться")
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    authorisationViewModel.authorised = true
                    authorisationViewModel.authoriseUser(
                        user = User(
                            id = 0,
                            username = username.value,
                            password = password.value
                        )
                    )
                    editor.apply {
                        putBoolean("authorised", true)
                        putString("token", authorisationViewModel.token)
                        putString("username", username.value)
                        commit()
                    }
                    navController.navigate(BSUIRJournalScreen.GroupList.name)
                },
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Войти")
            }
        }
    }
}