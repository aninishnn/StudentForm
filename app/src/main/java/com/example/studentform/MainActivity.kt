package com.example.studentform

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentFormTheme {
                StudentFormScreen()
            }
        }
    }
}

@Composable
fun StudentFormTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(
        background = Color(0xFFF8F3EA),
        surface = Color(0xFFFFFFFF),
        primary = Color(0xFF243447),
        secondary = Color(0xFF2B9C8F)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography,
        content = content
    )
}

@Composable
fun StudentFormScreen() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var nameState by rememberSaveable { mutableStateOf("") }
    var surnameState by rememberSaveable { mutableStateOf("") }
    var emailState by rememberSaveable { mutableStateOf("") }
    var dateState by rememberSaveable { mutableStateOf("") }
    var selectedOption by rememberSaveable { mutableStateOf("") }
    var isAgreed by rememberSaveable { mutableStateOf(false) }

    fun openDatePicker() {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                dateState = "%02d/%02d/%04d".format(day, month + 1, year)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F3EA))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 18.dp)
                .navigationBarsPadding()
                .padding(bottom = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            HeaderBlock()

            FormInput(
                title = "First Name",
                value = nameState,
                hint = "Enter first name",
                onChange = { nameState = it }
            )

            FormInput(
                title = "Last Name",
                value = surnameState,
                hint = "Enter last name",
                onChange = { surnameState = it }
            )

            FormInput(
                title = "Email",
                value = emailState,
                hint = "example@mail.com",
                onChange = { emailState = it }
            )

            DateInput(
                dateState = dateState,
                onClick = { openDatePicker() }
            )

            DirectionChooser(
                selectedOption = selectedOption,
                onSelect = { selectedOption = it }
            )

            AgreementRow(
                isAgreed = isAgreed,
                onChange = { isAgreed = it }
            )

            Button(
                onClick = {
                    val isFormReady = nameState.isNotBlank() &&
                        surnameState.isNotBlank() &&
                        emailState.isNotBlank() &&
                        dateState.isNotBlank() &&
                        selectedOption.isNotBlank() &&
                        isAgreed

                    if (isFormReady) {
                        Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2B9C8F),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Submit", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun HeaderBlock() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF243447), RoundedCornerShape(14.dp))
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(5.dp)
                .height(60.dp)
                .background(Color(0xFFF5A25D), RoundedCornerShape(8.dp))
        )
        Column(modifier = Modifier.padding(start = 14.dp)) {
            Text(
                text = "Student Form",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Fill in your details",
                color = Color(0xFFD8E2E1),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun FormInput(
    title: String,
    value: String,
    hint: String,
    onChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = title,
            color = Color(0xFF243447),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            placeholder = { Text(text = hint, color = Color(0xFF8C8C8C)) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = fieldColors()
        )
    }
}

@Composable
fun DateInput(dateState: String, onClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = "Date of Birth",
            color = Color(0xFF243447),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
        Box {
            OutlinedTextField(
                value = dateState,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                placeholder = { Text(text = "Select a date", color = Color(0xFF8C8C8C)) },
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Select date",
                        tint = Color(0xFF2B9C8F)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = fieldColors()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { onClick() }
            )
        }
    }
}

@Composable
fun DirectionChooser(
    selectedOption: String,
    onSelect: (String) -> Unit
) {
    val options = listOf("IT", "Design", "Business")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Study Program",
            color = Color(0xFF243447),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        if (selectedOption == option) Color(0xFFE7F5F3) else Color.White,
                        RoundedCornerShape(10.dp)
                    )
                    .border(
                        1.dp,
                        if (selectedOption == option) Color(0xFF2B9C8F) else Color(0xFFE0D7CC),
                        RoundedCornerShape(10.dp)
                    )
                    .clickable { onSelect(option) }
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onSelect(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF2B9C8F),
                        unselectedColor = Color(0xFF7D868C)
                    )
                )
                Text(
                    text = option,
                    color = Color(0xFF243447),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun AgreementRow(
    isAgreed: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFFE0D7CC), RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "I agree to the terms and conditions",
            modifier = Modifier.weight(1f),
            color = Color(0xFF243447),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Switch(
            checked = isAgreed,
            onCheckedChange = onChange,
            modifier = Modifier.size(width = 52.dp, height = 32.dp),
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF2B9C8F),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFF9CAAA4)
            )
        )
    }
}

@Composable
fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFF2B9C8F),
    unfocusedBorderColor = Color(0xFFE0D7CC),
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    cursorColor = Color(0xFF2B9C8F),
    focusedTextColor = Color(0xFF243447),
    unfocusedTextColor = Color(0xFF243447)
)

@Preview(showBackground = true)
@Composable
fun StudentFormPreview() {
    StudentFormTheme {
        StudentFormScreen()
    }
}
