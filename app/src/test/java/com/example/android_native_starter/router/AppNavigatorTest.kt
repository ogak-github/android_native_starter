package com.example.android_native_starter.router

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

object TestKey1 : NavKey
object TestKey2 : NavKey

class AppNavigatorTest {

    private lateinit var navigator: AppNavigator
    private val backStack = mutableStateListOf<NavKey>()

    @Before
    fun setup() {
        backStack.clear()
        navigator = AppNavigator(backStack)
    }

    @Test
    fun `push adds key to backstack`() {
        navigator.push(TestKey1)
        assertEquals(1, backStack.size)
        assertEquals(TestKey1, backStack.last())
    }

    @Test
    fun `pop removes last key`() {
        navigator.push(TestKey1)
        navigator.push(TestKey2)
        
        navigator.pop()
        
        assertEquals(1, backStack.size)
        assertEquals(TestKey1, backStack.last())
    }

    @Test
    fun `clearAndPush clears stack and adds new key`() {
        navigator.push(TestKey1)
        
        navigator.clearAndPush(TestKey2)
        
        assertEquals(1, backStack.size)
        assertEquals(TestKey2, backStack.last())
    }
}
