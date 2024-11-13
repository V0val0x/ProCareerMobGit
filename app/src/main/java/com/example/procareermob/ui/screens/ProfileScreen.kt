package com.example.procareermob.ui.screens


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import coil.compose.rememberAsyncImagePainter
import com.example.procareermob.model.UserProfile
import com.example.procareermob.viewmodel.ProfileViewModel
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val editProfileSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val avatarSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    var avatarBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var avatarUrl by remember { mutableStateOf<String?>(null) }
    var userName by remember { mutableStateOf("Anton Petushok") }
    var userEmail by remember { mutableStateOf("example@mail.com") }

    var tempUserName by remember { mutableStateOf(userName) }
    var tempUserEmail by remember { mutableStateOf(userEmail) }

    //val storage = FirebaseStorage.getInstance() в случае использования firebase

    // Загрузка аватара из локального хранилища при запуске
    LaunchedEffect(Unit) {
        val bitmap = loadAvatarFromInternalStorage(context)
        if (bitmap != null) {
            avatarBitmap = bitmap.asImageBitmap()
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val inputStream = context.contentResolver.openInputStream(it)
                inputStream?.let { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)
                    avatarBitmap = bitmap.asImageBitmap()

                    // Сохраняем аватар в локальное хранилище
                    saveAvatarToInternalStorage(context, bitmap)

                    //Вариант с Firebase
                        /*saveAvatarToFirebase(bitmap, storage) { downloadUrl ->
                        avatarUrl = downloadUrl
                        }
                         */
                }
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            bitmap?.let {
                avatarBitmap = it.asImageBitmap()

                saveAvatarToInternalStorage(context, it)
                //Вариант с Firebase
                /*saveAvatarToFirebase(it, storage) { downloadUrl ->
                    avatarUrl = downloadUrl
                    }
                 */
            }
        }
    )

    fun showAvatarOptions() {
        coroutineScope.launch { avatarSheetState.show() }
    }

    ModalBottomSheetLayout(
        sheetState = avatarSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Выбрать изображение из галереи",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            imagePickerLauncher.launch("image/*")
                            coroutineScope.launch { avatarSheetState.hide() }
                        }
                        .padding(16.dp)
                )
                Divider()
                Text(
                    text = "Сделать фото",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            cameraLauncher.launch(null)
                            coroutineScope.launch { avatarSheetState.hide() }
                        }
                        .padding(16.dp)
                )
            }
        }
    ) {
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
                    EditProfileContent(
                        tempUserName = tempUserName,
                        tempUserEmail = tempUserEmail,
                        onUserNameChange = { tempUserName = it },
                        onUserEmailChange = { tempUserEmail = it },
                        onSaveChanges = {
                            // Обновляем основные данные при сохранении
                            userName = tempUserName
                            userEmail = tempUserEmail
                            coroutineScope.launch { editProfileSheetState.hide() }
                        },
                        onDismiss = { coroutineScope.launch { editProfileSheetState.hide() } }
                    )
                }
            ) {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text("Мой профиль", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3A6EFF))
                            },
                            actions = {
                                IconButton(onClick = { coroutineScope.launch { bottomSheetState.show() } }) {
                                    Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = "Меню", tint = Color.Black)
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
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

                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .clickable {  showAvatarOptions() },
                            contentAlignment = Alignment.Center
                        ) {
                            if (avatarBitmap != null) {
                                Image(bitmap = avatarBitmap!!, contentDescription = "User Avatar", contentScale = ContentScale.Crop)
                            } else if (avatarUrl != null) {
                                Image(painter = rememberAsyncImagePainter(avatarUrl), contentDescription = "User Avatar", contentScale = ContentScale.Crop)
                            } else {
                                Image(painter = painterResource(id = R.drawable.ic_avatar_placeholder), contentDescription = "User Avatar", contentScale = ContentScale.Crop)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(userName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3A6EFF))
                        Text(userEmail, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF3A6EFF))

                        Spacer(modifier = Modifier.height(16.dp))

                        // Карточка резюме
                        ResumeCard()

                        Spacer(modifier = Modifier.height(16.dp))

                        // Кнопка "Мой роудмап"
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

                        // Кнопка "Тесты"
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
}

@Composable
fun EditProfileContent(
    tempUserName: String,
    tempUserEmail: String,
    onUserNameChange: (String) -> Unit,
    onUserEmailChange: (String) -> Unit,
    onSaveChanges: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Редактировать профиль", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3A6EFF))
        Spacer(modifier = Modifier.height(8.dp))

        // Поля для редактирования имени и email
        OutlinedTextField(
            value = tempUserName,
            onValueChange = onUserNameChange,
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = tempUserEmail,
            onValueChange = onUserEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Сохранить изменения"
        Button(
            onClick = onSaveChanges,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3A6EFF)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить изменения", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Кнопка "Отмена"
        Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Отмена", color = Color.White)
        }
    }
}

// Функция для сохранения аватара в Firebase Storage
fun saveAvatarToFirebase(bitmap: Bitmap, storage: FirebaseStorage, onSuccess: (String) -> Unit) {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    val imageData = outputStream.toByteArray()
    val avatarRef = storage.reference.child("avatars/${UUID.randomUUID()}.jpg")

    avatarRef.putBytes(imageData)
        .addOnSuccessListener {
            avatarRef.downloadUrl.addOnSuccessListener { uri ->
                Log.d("Firebase", "Image uploaded successfully: $uri")
                onSuccess(uri.toString())
            }
        }
        .addOnFailureListener { exception ->
            Log.e("Firebase", "Image upload failed: ${exception.message}")
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

fun saveAvatarToInternalStorage(context: Context, bitmap: Bitmap): Boolean {
    return try {
        val file = File(context.filesDir, "user_avatar.png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

// Загрузка аватара из внутреннего хранилища
fun loadAvatarFromInternalStorage(context: Context): Bitmap? {
    return try {
        val file = File(context.filesDir, "user_avatar.png")
        if (file.exists()) {
            val inputStream = FileInputStream(file)
            BitmapFactory.decodeStream(inputStream)
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Удаление аватара из внутреннего хранилища (опционально)
fun deleteAvatarFromInternalStorage(context: Context): Boolean {
    return try {
        val file = File(context.filesDir, "user_avatar.png")
        file.delete()
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

