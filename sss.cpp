#include <iostream>
#include <string>
using namespace std;

// -------------------------
// 🔹 Node class (ใช้สำหรับ Stack และ Queue)
// -------------------------
class Node {
public:
    int data;
    Node* next;

    Node(int data) {
        this->data = data;
        this->next = nullptr;
    }
};

// -------------------------
// 🔹 Stack class (LIFO)
// -------------------------
class Stack {
private:
    Node* top;

public:
    Stack() {
        top = nullptr;
    }

    void push(int value) {
        Node* newNode = new Node(value);
        newNode->next = top;
        top = newNode;
    }

    void pop() {
        if (top == nullptr) {
            cout << "Stack is empty\n";
            return;
        }
        Node* temp = top;
        top = top->next;
        delete temp;
    }

    void display() {
        Node* current = top;
        cout << "Stack: ";
        while (current != nullptr) {
            cout << current->data << " ";
            current = current->next;
        }
        cout << endl;
    }
};

// -------------------------
// 🔹 Queue class (FIFO)
// -------------------------
class Queue {
private:
    Node* front;
    Node* rear;

public:
    Queue() {
        front = rear = nullptr;
    }

    void enqueue(int value) {
        Node* newNode = new Node(value);
        if (rear == nullptr) {
            front = rear = newNode;
            return;
        }
        rear->next = newNode;
        rear = newNode;
    }

    void dequeue() {
        if (front == nullptr) {
            cout << "Queue is empty\n";
            return;
        }
        Node* temp = front;
        front = front->next;
        if (front == nullptr) {
            rear = nullptr;
        }
        delete temp;
    }

    void display() {
        Node* current = front;
        cout << "Queue: ";
        while (current != nullptr) {
            cout << current->data << " ";
            current = current->next;
        }
        cout << endl;
    }
};

// -------------------------
// 🔹 Simple Hash Table class (with separate chaining)
// -------------------------
const int TABLE_SIZE = 10;

class HashNode {
public:
    string key;
    int value;
    HashNode* next;

    HashNode(string key, int value) {
        this->key = key;
        this->value = value;
        this->next = nullptr;
    }
};

class HashTable {
private:
    HashNode* table[TABLE_SIZE];

    int hashFunction(string key) {
        int hash = 0;
        for (char ch : key) {
            hash = (hash + int(ch)) % TABLE_SIZE;
        }
        return hash;
    }

public:
    HashTable() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            table[i] = nullptr;
        }
    }

    void insert(string key, int value) {
        int index = hashFunction(key);
        HashNode* newNode = new HashNode(key, value);

        if (table[index] == nullptr) {
            table[index] = newNode;
        } else {
            HashNode* current = table[index];
            while (current->next != nullptr) {
                current = current->next;
            }
            current->next = newNode;
        }
    }

    void display() {
        cout << "Hash Table:\n";
        for (int i = 0; i < TABLE_SIZE; i++) {
            cout << "[" << i << "] ";
            HashNode* current = table[i];
            while (current != nullptr) {
                cout << "(" << current->key << ": " << current->value << ") -> ";
                current = current->next;
            }
            cout << "nullptr\n";
        }
    }
};

// -------------------------
// 🔹 Main Function
// -------------------------
int main() {
    // Stack Test
    Stack stack;
    stack.push(10);
    stack.push(20);
    stack.push(30);
    stack.display();
    stack.pop();
    stack.display();

    cout << "--------------------\n";

    // Queue Test
    Queue queue;
    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);
    queue.display();
    queue.dequeue();
    queue.display();

    cout << "--------------------\n";

    // Hash Table Test
    HashTable hashTable;
    hashTable.insert("John", 123);
    hashTable.insert("Mike", 456);
    hashTable.insert("Sara", 789);
    hashTable.insert("Jane", 999);
    hashTable.display();

    return 0;
}
