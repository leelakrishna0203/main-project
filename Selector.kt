package com.example.bustract.Interactions

import com.example.bustract.Responses.Model.User

interface Selector {
fun state(user: User)
}