package org.example.codegeneration;

import java.util.Stack;

import java.util.HashMap;
import java.util.Map;

public class RegisterStack {
    private Map<String, Stack<String>> registerStacks;

    public RegisterStack() {
        registerStacks = new HashMap<>();
    }

    public void push(String registerSetName, String register) {
        Stack<String> stack = registerStacks.computeIfAbsent(registerSetName, k -> new Stack<>());
        stack.push(register);
    }

    public String pop(String registerSetName) {
        Stack<String> stack = registerStacks.get(registerSetName);
        if (stack != null && !stack.isEmpty()) {
            return stack.pop();
        }
        return null; // Or throw an exception indicating an empty stack
    }

    public boolean isEmpty(String registerSetName) {
        Stack<String> stack = registerStacks.get(registerSetName);
        return stack == null || stack.isEmpty();
    }

    public int size(String registerSetName) {
        Stack<String> stack = registerStacks.get(registerSetName);
        return stack != null ? stack.size() : 0;
    }

    public void clear(String registerSetName) {
        Stack<String> stack = registerStacks.get(registerSetName);
        if (stack != null) {
            stack.clear();
        }
    }

    @Override
    public String toString() {
        return "RegisterStack{" +
                "registerStacks=" + registerStacks +
                '}';
    }
}
