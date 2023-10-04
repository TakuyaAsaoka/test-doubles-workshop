package com.example.missile

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.Exception

class MissileLauncherTests {
	@Test
	fun `launchCodeのisExpired()が偽の場合、isSigned()が偽ならdisable()は呼ばれるがlaunch()が実行されない`() {
		val launcher = MissileLauncher()
		val missile = MockMissile()
		val launchCode = UnsignedUnexpiredStubLaunchCode()

		launcher.launchMissile(missile, launchCode)

		missile.assert()
	}

	@Test
	fun `launchCodeのisExpired()が偽の場合、isSigned()が真ならdisable()は呼ばれないが、launch()が呼ばれる`(){
		val launcher = MissileLauncher()
		val missile = SpyMissile()
		val launchCode = SignedUnexpiredStubLaunchCode()

		launcher.launchMissile(missile, launchCode)

		assertEquals(false, missile.disable_was_called)
		assertEquals(true,missile.launch_was_called)
	}

	@Test
	fun `launchCodeのisExpired()が真の場合、isSigned()が真ならmissileのdisable()は呼ばれるがlaunch()を呼ばない`(){
		val launcher = MissileLauncher()
		val missile = SpyMissile()
		val signedLaunchCode = SignedExpiredStubLaunchCode()

		launcher.launchMissile(missile, signedLaunchCode)

		assertEquals(true, missile.disable_was_called)
		assertEquals(false,missile.launch_was_called)
	}

	@Test
	fun `launchCodeのisExpired()が真の場合、isSigned()が偽ならmissileのdisable()は呼ばれるがlaunch()を呼ばない`(){
		val launcher = MissileLauncher()
		val missile = SpyMissile()
		val unsignedLaunchCode = UnsignedExpiredStubLaunchCode()

		launcher.launchMissile(missile, unsignedLaunchCode)

		assertEquals(true, missile.disable_was_called)
		assertEquals(false,missile.launch_was_called)
	}

	@Test
	fun `launchCodeのisExpired()が真の場合、isSigned()の真ならmissileのdisable()を呼んでlaunch()を呼ばない`() {
		val launcher = MissileLauncher()
		val missile = SpyMissile()
		val signedLaunchCode = SignedExpiredStubLaunchCode()

		launcher.launchMissile(missile, signedLaunchCode)

		assertEquals(true, missile.disable_was_called)
		assertEquals(false, missile.launch_was_called)
	}

	@Test
	fun `launchCodeのisExpired()が真の場合、isSigned()の偽ならmissileのdisable()を呼んでlaunch()を呼ばない`() {
		val launcher = MissileLauncher()
		val missile = SpyMissile()
		val unsignedLaunchCode = UnsignedExpiredStubLaunchCode()

		launcher.launchMissile(missile, unsignedLaunchCode)

		assertEquals(true, missile.disable_was_called)
		assertEquals(false, missile.launch_was_called)
	}
}

class DummyMissile(): Missile {
	override fun launch() {
		throw Exception("このメソッドは実行されるべきではありません。")
	}
	override fun disable() {
	}
}

class SpyMissile(): Missile {
	var launch_was_called = false
	var disable_was_called = false
	override fun launch() {
		launch_was_called = true
	}
	override fun disable() {
		disable_was_called = true
	}
}
class MockMissile(): Missile {
	var launch_was_called = false
	var disable_was_called = false
	override fun launch() {
		launch_was_called = true
	}
	override fun disable() {
		disable_was_called = true
	}

	fun assert() {
		assertEquals(true, this.disable_was_called)
		assertEquals(false, this.launch_was_called)
	}
}

class SignedExpiredStubLaunchCode(): LaunchCode {
	override fun isSigned(): Boolean {
		return true
	}
	override fun isExpired(): Boolean {
		return true
	}
}
class UnsignedExpiredStubLaunchCode(): LaunchCode {
	override fun isSigned(): Boolean {
		return false
	}
	override fun isExpired(): Boolean {
		return true
	}
}

class SignedUnexpiredStubLaunchCode(): LaunchCode {
	override fun isSigned(): Boolean {
		return true
	}
	override fun isExpired(): Boolean {
		return false
	}
}

class UnsignedUnexpiredStubLaunchCode(): LaunchCode {
	override fun isSigned(): Boolean {
		return false
	}
	override fun isExpired(): Boolean {
		return false
	}
}

