package application.controller;

import javafx.scene.control.TextField;

public class RulesTextField extends TextField{

	    public RulesTextField() {
	        this.setMinWidth(10);
	        this.setMaxWidth(10);
	    }

	    public void replaceText(int start, int end, String text) {
	    	String oldValue = getText();
	    	if (!text.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")) {
	            super.replaceText(start, end, text);
	        }
	        if (getText().length() > 10 ) {
	            setText(oldValue);
	        }
	    }

	    public void replaceSelection(String text) {
	    	String oldValue = getText();
	    	if (!text.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")) {
	            super.replaceSelection(text);
	        }
	        if (getText().length() > 10 ) {
	            setText(oldValue);
	        }
	    }
	
}
