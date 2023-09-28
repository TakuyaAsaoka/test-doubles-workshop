package com.example.missile

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.Exception

class MissileLauncherTests {
	@Test
	fun `launchCodeのisSigned()が偽ならlaunch()が実行されない`() {
		val launcher = MissileLauncher()
		val missile = DummyMissile()
		val launchCode = UnsignedExpiredStubLaunchCode()

		launcher.launchMissile(missile, launchCode)
	}

	@Test
	fun `launchCodeのisSigned()が真ならlaunch()が呼ばれる`(){
		val launcher = MissileLauncher()
		val missile = SpyMissile()
		val launchCode = SignedExpiredStubLaunchCode()

		launcher.launchMissile(missile, launchCode)

		assertEquals(true,missile.wasCalled)
	}

	@Test
	fun `launchCodeのisExpired()が真の場合、isSignedの値に関わらず、missileのlaunch()を呼ばない`(){
		val launcher = MissileLauncher()
		val missile = SpyMissile()
		val signedLaunchCode = SignedExpiredStubLaunchCode()
		val unsignedLaunchCode = UnsignedExpiredStubLaunchCode()

		launcher.launchMissile(missile, signedLaunchCode)

		assertEquals(false,missile.wasCalled)

		launcher.launchMissile(missile, unsignedLaunchCode)

		assertEquals(false,missile.wasCalled)
	}

}



class DummyMissile(): Missile {
	override fun launch() {
		throw Exception("このメソッドは実行されるべきではありません。")
	}
}
//class UnsignedStubLaunchCode(): LaunchCode {
//	override fun isSigned(): Boolean {
//		return false
//	}
//
//}
//
//class SignedStubLaunchCode(): LaunchCode {
//	override fun isSigned(): Boolean {
//		return true
//	}
//}
class SpyMissile(): Missile {
	var wasCalled = false
	override fun launch() {
		wasCalled = true
	}
}

//class StubLaunchCode(): LaunchCode {
//	var isSigned_return_value = null
//	var isExpired_return_value = null
//
//	override fun isSigned(): Boolean {
//		return isSigned_return_value!!
//	}
//	override fun isExpired(): Boolean {
//		return isExpired_return_value!!
//	}
//}

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

//class SignedUnexpiredStubLaunchCode(): LaunchCode {
//	override fun isSigned(): Boolean {
//		return true
//	}
//	override fun isExpired(): Boolean {
//		return false
//	}
//}
//
//class UnsignedUnexpiredStubLaunchCode(): LaunchCode {
//	override fun isSigned(): Boolean {
//		return false
//	}
//	override fun isExpired(): Boolean {
//		return false
//	}
//}