package com.criptext.uisample;

import android.content.Intent;
import android.os.Bundle;

import com.criptext.monkeykitui.MonkeyChatFragment;
import com.criptext.monkeykitui.MonkeyConversationsFragment;
import com.criptext.monkeykitui.conversation.ConversationsActivity;
import com.criptext.monkeykitui.conversation.MonkeyConversation;
import com.criptext.monkeykitui.input.listeners.InputListener;
import com.criptext.monkeykitui.recycler.MonkeyItem;
import com.criptext.monkeykitui.recycler.audio.DefaultVoiceNotePlayer;
import com.criptext.monkeykitui.recycler.audio.VoiceNotePlayer;
import com.criptext.monkeykitui.util.MonkeyFragmentManager;
import com.criptext.uisample.conversation.FakeConversations;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gesuwall on 8/10/16.
 */
public class MainFragmentActivity extends BaseChatActivity implements ConversationsActivity {
    MonkeyChatFragment chatFragment;
    MonkeyConversationsFragment convFragment;
    VoiceNotePlayer vnPlayer;
    InputListener inputListener;
    MonkeyFragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = new MonkeyFragmentManager(this);
        fragmentManager.setContentLayout(savedInstanceState);
    }




    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    void rebindMonkeyItem(MonkeyItem message) {
        if(chatFragment != null)
        chatFragment.rebindMonkeyItem(message);
    }

    @Override
    void addOldMessages(ArrayList<MonkeyItem> messages, boolean hasReachedEnd) {
        if(chatFragment != null)
            chatFragment.addOldMessages(messages, hasReachedEnd);
    }

    @Override
    void smoothlyAddNewItem(MonkeyItem message) {
        chatFragment.smoothlyAddNewItem(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Activity must manually call chatFragments on activityResult. it's not automatic.
        //This makes sure that the inputListener receives the edited photos that the user wants to send
        if(chatFragment != null)
            chatFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConversationClicked(@NotNull MonkeyConversation conversation) {
        if(inputListener == null)
            inputListener = createInputListener();
        if(vnPlayer == null)
            vnPlayer = new DefaultVoiceNotePlayer(this);
        MonkeyChatFragment fragment = MonkeyChatFragment.Companion.newInstance("0", false);
        fragmentManager.setChatFragment(fragment, inputListener, vnPlayer);
    }

    @Override
    public void requestConversations() {
        if(convFragment != null)
            convFragment.insertConversations(new FakeConversations().getAll(this), true);
    }

    @Override
    public void setChatFragment(@Nullable MonkeyChatFragment monkeyChatFragment) {
        chatFragment = monkeyChatFragment;
    }

    @Override
    public void setConversationsFragment(@Nullable MonkeyConversationsFragment monkeyConversationsFragment) {
        convFragment = monkeyConversationsFragment;
    }

    @Override
    public void retainMessages(@NotNull String conversationId, @NotNull Collection<? extends MonkeyItem> messages) {

    }

    @Override
    public void onLoadMoreConversations(int loadedItems) {

    }
}