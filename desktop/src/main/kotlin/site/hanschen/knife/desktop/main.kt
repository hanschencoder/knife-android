package site.hanschen.knife.desktop

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.android.ddmlib.AndroidDebugBridge
import com.android.ddmlib.Log
import java.util.concurrent.TimeUnit

@Composable
@Preview
fun App() {
    Log.setLevel(Log.LogLevel.VERBOSE)
    AndroidDebugBridge.init(false)

    var debugBridge = AndroidDebugBridge.createBridge("/home/chenhang/android-sdk-linux/platform-tools/adb", false, Long.MAX_VALUE, TimeUnit.MILLISECONDS)
    MaterialTheme {


        var name = remember {
            if (debugBridge.devices.isNotEmpty()) {
                mutableStateOf(debugBridge.devices[0].name)
            } else {
                mutableStateOf("null")
            }

        }
        AndroidDebugBridge.addDeviceChangeListener(object : AndroidDebugBridge.IDeviceChangeListener {
            override fun deviceConnected(device: com.android.ddmlib.IDevice?) {
                name.value = device?.name ?: "null"
            }

            override fun deviceDisconnected(device: com.android.ddmlib.IDevice?) {
                name.value = "null"
            }

            override fun deviceChanged(device: com.android.ddmlib.IDevice?, changeMask: Int) {
                name.value = device?.name ?: "null"
            }
        })

        Row {
            Column {
                Text("      Hello, Desktop!      " + name.value)
                Text("      Hello, Desktop!")
            }

            Column {
                Text("      Hello, Desktop!")
                Text("      Hello, Desktop!")
            }
        }
    }
}

fun main() {
    println("hello world")
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Knife for Android",
            state = rememberWindowState(width = 1600.dp, height = 1080.dp)
        ) {
            App()
        }
    }
}
