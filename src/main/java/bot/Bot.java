package bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
//import bot.Menu.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;
public class Bot extends TelegramLongPollingBot{

    private boolean start = false;
    private InlineKeyboardMarkup keyboardM1;
    private InlineKeyboardMarkup keyboardM2;
    //main method start
    Bot(){
        var next = InlineKeyboardButton.builder()
                .text("Next").callbackData("next")
                .build();
        var back = InlineKeyboardButton.builder()
                .text("back").callbackData("back")
                .build();
        var url = InlineKeyboardButton.builder()
                .text("YouTube")
                .url("www.youtube.com")
                .build();

        keyboardM1 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(next)).build();
        //buttons are wrapped in lists since each keyboard is a set of button rows
        keyboardM2 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(back))
                .keyboardRow(List.of(url))
                .build();
    }
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new Bot());
    }
    //main method end

    //send response text to telegram
    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try{
            execute(sm);
        }catch(TelegramApiException e){
            throw new RuntimeException(e);
        }

    }
    //end send msg

    //realtime update msg
    @Override
    public void onUpdateReceived(Update update) {
        Menu m = new Menu();
        var msg = update.getMessage();
        var chatId = msg.getChatId();
        var user = msg.getFrom();
        //sendText(user.getId(), msg.getText());
        //System.out.println(user.getFirstName()+ msg.getText());
        var txt = msg.getText();
       if(msg.isCommand()){
            if(txt.equals("/scream")){
                sendText(chatId, txt);
            }else if (txt.equals("/whishper")){
                sendText(user.getId(), txt);
            }else if (txt.equals("/menu")){
                m.sendMenu(chatId,"<b>This Is Your Menu</b>",keyboardM1);
            }else if (txt.equals("/option")){
                //m.crateMenu(user.getId(),msg.toString());
            }else{
                m.sendMenu(chatId, "<b>ඔබ සොයන්නේ. </b> "+txt ,keyboardM2);
            }
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

}