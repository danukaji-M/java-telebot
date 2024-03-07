package bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;


public class Menu extends TelegramLongPollingBot{

/*    //public void crateMenu(Long who, String What){

            ReplyKeyboardMarkup km = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow row1 = new KeyboardRow();
            row1.add("Option 1");
            row1.add("Option 2");
            KeyboardRow row2 = new KeyboardRow();
            row2.add("Option 3");
            row2.add("Option 4");
            keyboard.add(row1);
            keyboard.add(row2);
            km.setKeyboard(keyboard);
            km.setResizeKeyboard(true);

            SendMessage sm = new SendMessage().builder()
                    .chatId(who.toString())
                    .text("www.youtube.com")
                    .replyMarkup(km).build();

            try{
                execute(sm);
            }catch(TelegramApiException e){
                e.printStackTrace();
            }
    }
*/


    public void sendMenu(Long who, String text, InlineKeyboardMarkup kb){
        SendMessage sm = new SendMessage().builder()
                .chatId(who.toString())
                .parseMode("HTML")
                .text(text)
                .replyMarkup(kb).build();
        try{
            execute(sm);
        }catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotToken(){
        return "6778683678:AAFYV0YnqfZwVPCashYS-9vcsPmC1aFEMFc";
    }

    @Override
    public String getBotUsername(){
        return "myBot-1";
    }

    @Override
    public void onUpdateReceived(Update update) {

   }
}