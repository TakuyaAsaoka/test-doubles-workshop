package com.example.missile

class MissileLauncher {

    fun launchMissile(missile: Missile, launchCode: LaunchCode): Unit {
        if (launchCode.isSigned()){
            missile.launch()
        }
    }
}