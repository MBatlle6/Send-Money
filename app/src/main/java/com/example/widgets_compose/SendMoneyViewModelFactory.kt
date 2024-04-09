import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.SharedPreferencesData

@Suppress("UNCHECKED_CAST")
class SendMoneyViewModelFactory(private val sharedPreferencesData: SharedPreferencesData) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SendMoneyViewModel::class.java)) {
            return SendMoneyViewModel(sharedPreferencesData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}