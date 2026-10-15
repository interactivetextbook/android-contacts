package ru.yandex.practicum.contacts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    private val textViewModel: TextViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // НАПРАВЛЕНИЕ: Сейчас запущена самостоятельная работа с ViewModel для сдачи урока.
                    // Чтобы переключиться обратно, замените на MainScreen() или ShoppingCartScreen()
                    TextFieldWithViewModel(viewModel = textViewModel)
                }
            }
        }
    }
}

// =================================================================
// 1. КОД ДЛЯ ТЕКУЩЕГО УРОКА ("VIEWMODEL + SCAFFOLD + STATEFLOW")
// =================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithViewModel(viewModel: TextViewModel) {
    val text = viewModel.text.collectAsState().value
    val title = viewModel.title.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.updateTitle(text) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить"
                )
            }
        }
    ) { paddingValues ->
        TextField(
            modifier = Modifier.padding(paddingValues),
            value = text,
            onValueChange = { newText ->
                viewModel.onTextChanged(newText)
            },
            label = { Text("Введите текст") }
        )
    }
}

// =================================================================
// 2. КОД ДЛЯ ПРОЕКТА "PLAYLIST MAKER" (СОХРАНЕН)
// =================================================================
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val showToast = { buttonName: String ->
        Toast.makeText(context, "Нажата кнопка \"$buttonName\"", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2196F3))
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Playlist Maker", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Medium)
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MenuRowButton(title = "Поиск", onClick = {
                val intent = Intent(context, SearchActivity::class.java)
                context.startActivity(intent)
            })
            MenuRowButton(title = "Плейлисты", onClick = { showToast("Плейлисты") })
            MenuRowButton(title = "Избранное", onClick = { showToast("Избранное") })
            MenuRowButton(title = "Настройки", onClick = {
                val intent = Intent(context, SettingsActivity::class.java)
                context.startActivity(intent)
            })
        }
    }
}

@Composable
fun MenuRowButton(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().height(56.dp).clickable(onClick = onClick).padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 18.sp, color = Color.Black)
        Text(text = " > ", fontSize = 18.sp, color = Color.LightGray)
    }
}

// =================================================================
// 3. КОД ДЛЯ УРОКА ПО СЛУЖЕБНЫМ САЙД-ЭФФЕКТАМ (КОРЗИНА)
// =================================================================
data class Product(val id: Int, val name: String, val price: Int)

@Composable
fun ShoppingCartScreen() {
    val context = LocalContext.current
    var products by remember {
        mutableStateOf(listOf(Product(0, "Товар #1", 100), Product(1, "Товар #2", 150), Product(2, "Товар #3", 56)))
    }
    val totalSum = remember(products) { products.sumOf { it.price } }
    val isCartEmpty = products.isEmpty()
    val isSumOver500 = totalSum > 500

    LaunchedEffect(isSumOver500) {
        if (isSumOver500) {
            Toast.makeText(context, "Доставка бесплатная!", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Корзина товаров", fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 24.dp))
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            products.forEach { product ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = product.name, fontSize = 18.sp)
                    Text(text = "${product.price} ₽", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Итого:", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(text = "$totalSum ₽", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.height(48.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                val nextId = (products.maxOfOrNull { it.id } ?: -1) + 1
                products = products + Product(nextId, "Товар #$nextId", (100..300).random())
            }) {
                Text(text = "Добавить товар")
            }
            if (!isCartEmpty) {
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { if (products.isNotEmpty()) products = products.dropLast(1) }) {
                    Text(text = "Удалить товар")
                }
            }
        }
    }
}

// =================================================================
// PREVIEWS
// =================================================================
@Preview(showBackground = true, name = "Урок: ViewModel и Scaffold")
@Composable
fun TextFieldWithViewModelPreview() {
    MaterialTheme {
        TextFieldWithViewModel(viewModel = TextViewModel())
    }
}
