package com.example.missile

class MissileLauncher {
    fun launchMissile(missile: Missile, launchCode: LaunchCode): Unit {
        if (!launchCode.isExpired() && launchCode.isSigned()) {
            missile.launch()
        } else {
            missile.disable()
        }
    }
}