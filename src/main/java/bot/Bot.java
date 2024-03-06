package bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

//telegram bot name
public class Bot extends TelegramLongPollingBot{
    private boolean screaming = false;
    private InlineKeyboardMarkup keyboardM1;
    private InlineKeyboardMarkup keyboardM2;

    //Inline keyBoard Setup
    private void setupKeyBoard(){
        var next = InlineKeyboardButton.builder()
                .text("Next").callbackData("next")
                .build();

        var back = InlineKeyboardButton.builder()
                .text("Back").callbackData("back")
                .build();

        var url = InlineKeyboardButton.builder()
                .text("Tutorial")
                .url("https://core.telegram.org/bots/api")
                .build();

        keyboardM1 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(next)).build();

        //Buttons are wrapped in lists since each keyboard is a set of button rows
        keyboardM2 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(back))
                .keyboardRow(List.of(url))
                .build();
    }
    public void sendMenu(Long who, String txt,InlineKeyboardButton kb){
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode("HTML").text(txt)
                .replyMarkup((ReplyKeyboard) kb).build();
        try{
            execute(sm);
        }catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getBotUsername() {
        return "TestBot_1";
    }
//set bot token
    @Override
    public String getBotToken(){
        return "6778683678:AAFYV0YnqfZwVPCashYS-9vcsPmC1aFEMFc";
    }

    //get msg details
    @Override
    public void onUpdateReceived(Update update){
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();
        //sendText(id,"Hello World");
        if(screaming) {
            scream(id, update.getMessage());
        }else{
            copyMessage(id, msg.getMessageId());
        }
        if(msg.isCommand()){
            if(msg.getText().equals("/scream")){
                screaming = true;
            }else if(msg.getText().equals("/whisper")){
                screaming = false;
            }
            return;
        }

    }

    private void scream(Long id, @org.jetbrains.annotations.NotNull Message msg){
        if(msg.hasText()){
            sendText(id,msg.getText().toUpperCase());
        }else{
            copyMessage(id, msg.getMessageId()); //we cant really scream a sticker
        }
    }

    //get text msg type
    public void sendText(Long who,String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //who are we sending a message to
                .text(what).build(); //message content
        try{
            execute(sm);
        }catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
    //main method
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot();
        botsApi.registerBot(bot);
        bot.sendText(1234L,"Hello World");


    }

    //copy message
    public void copyMessage(Long who, Integer msgId){
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(who.toString()) //We copy from the user
                .chatId(who.toString())
                .messageId(msgId)
                .build();
        try{
            execute(cm);
        }catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }

}
