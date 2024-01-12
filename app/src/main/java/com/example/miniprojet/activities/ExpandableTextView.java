package com.example.miniprojet.activities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miniprojet.R;

public class ExpandableTextView extends LinearLayout {
    private TextView questionTextView;
    private TextView answerTextView;

    public ExpandableTextView(Context context) {
        super(context);
        init(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.expandable_text_view, this);
        questionTextView = findViewById(R.id.questionTextView);
        answerTextView = findViewById(R.id.answerTextView);

        setOrientation(VERTICAL);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    public void setQuestion(String question) {
        questionTextView.setText(question);
    }

    public void setAnswer(String answer) {
        answerTextView.setText(answer);
    }

    public void toggle() {
        answerTextView.setVisibility(answerTextView.getVisibility() == VISIBLE ? GONE : VISIBLE);
    }
}
