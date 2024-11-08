package com.example.procareermob.ui.screens


import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procareermob.R
import kotlinx.coroutines.launch
import java.io.InputStream
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val editProfileSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    // Состояние для хранения URI аватара и ImageBitmap
    var avatarUri by remember { mutableStateOf<Uri?>(null) }
    var avatarBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            avatarUri = uri
            uri?.let {
                val inputStream: InputStream? = context.contentResolver.openInputStream(it)
                inputStream?.let { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)
                    avatarBitmap = bitmap.asImageBitmap()
                }
            }
        }
    )

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            ProfileMenu(
                onEditProfileClick = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        editProfileSheetState.show()
                    }
                },
                onDismiss = { coroutineScope.launch { bottomSheetState.hide() } }
            )
        }
    ) {
        ModalBottomSheetLayout(
            sheetState = editProfileSheetState,
            sheetContent = {
                EditProfileContent(onDismiss = { coroutineScope.launch { editProfileSheetState.hide() } })
            }
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Мой профиль",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF3A6EFF)
                            )
                        },
                        actions = {
                            IconButton(onClick = {
                                coroutineScope.launch { bottomSheetState.show() }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_menu),
                                    contentDescription = "Меню",
                                    tint = Color.Black // Иконка черного цвета
                                )
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent // Убираем фон TopAppBar
                        ),
                    )
                },
                bottomBar = { BottomNavigationBar(navController) }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Аватар пользователя
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.Gray, shape = CircleShape)
                            .clickable {
                                imagePickerLauncher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (avatarBitmap != null) {
                            Image(
                                bitmap = avatarBitmap!!,
                                contentDescription = "User Avatar",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.ic_avatar_placeholder),
                                contentDescription = "User Avatar",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Имя и профессия
                    Text("Антон Петухов", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3A6EFF))
                    Text("Безработный", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF3A6EFF))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Резюме
                    ResumeCard()

                    Spacer(modifier = Modifier.height(16.dp))

                    // Кнопки "Мой роудмап" и "Тесты"
                    Button(
                        onClick = { /* TODO: Перейти к роудмапу */ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF80AB)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Мой роудмап развития", fontSize = 16.sp, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { /* TODO: Перейти к тестам */ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3A6EFF)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Тесты", fontSize = 16.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileMenu(onEditProfileClick: () -> Unit, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            "Редактировать профиль",
            color = Color(0xFF3A6EFF),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable { onEditProfileClick() }
                .padding(16.dp)
        )
        Divider(color = Color.Gray.copy(alpha = 0.3f))
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Изменить резюме",
            color = Color(0xFF3A6EFF),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable { onDismiss() }
                .padding(16.dp)
        )
        Divider(color = Color.Gray.copy(alpha = 0.3f))
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Выйти из аккаунта",
            color = Color(0xFF3A6EFF),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable { onDismiss() }
                .padding(16.dp)
        )
    }
}

@Composable
fun EditProfileContent(onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(16.dp)
    ) {
        // Заголовок и кнопка закрытия
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Редактировать профиль",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3A6EFF)
            )
            IconButton(onClick = onDismiss) {
                Icon(painter = painterResource(id = R.drawable.ic_close), contentDescription = "Close")
            }
        }

        // Поля для редактирования
        TextFieldWithIcon(hint = "Имя", iconRes = R.drawable.ic_name)
        TextFieldWithIcon(hint = "Дата рождения", iconRes = R.drawable.ic_calendar)
        TextFieldWithIcon(hint = "Email", iconRes = R.drawable.ic_email)
        TextFieldWithIcon(hint = "Старый пароль", iconRes = R.drawable.ic_password, isPassword = true)
        TextFieldWithIcon(hint = "Новый пароль", iconRes = R.drawable.ic_password, isPassword = true)

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Сохранить изменения"
        Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3A6EFF)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(48.dp)
        ) {
            Text("Сохранить изменения", color = Color.White)
        }
    }
}

@Composable
fun TextFieldWithIcon(hint: String, iconRes: Int, isPassword: Boolean = false) {
    OutlinedTextField(
        value = "",
        onValueChange = { },
        label = { Text(hint) },
        leadingIcon = {
            Icon(painter = painterResource(id = iconRes), contentDescription = null)
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun ResumeCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFFE1E4FD),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Резюме.pdf", fontSize = 16.sp, color = Color(0xFF3A6EFF))
            Row {
                IconButton(onClick = { /* TODO: Просмотреть резюме */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_view), contentDescription = "Просмотреть")
                }
                IconButton(onClick = { /* TODO: Скачать резюме */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_download), contentDescription = "Скачать")
                }
                IconButton(onClick = { /* TODO: Поделиться резюме */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_share), contentDescription = "Поделиться")
                }
            }
        }
    }
}
