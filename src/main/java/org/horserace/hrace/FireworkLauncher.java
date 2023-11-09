package org.horserace.hrace;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FireworkLauncher extends BukkitRunnable {

    private JavaPlugin plugin;
    private World world;
    private Location location;

    public FireworkLauncher(JavaPlugin plugin, World world, Location location) {
        this.plugin = plugin;
        this.world = world;
        this.location = location;
    }

    private void launchFirework(Location location) {
        Firework firework = world.spawn(location, Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder()
                .withColor(Color.RED, Color.GREEN, Color.BLUE)
                .withFlicker()
                .withTrail()
                .withFade(Color.YELLOW)
                .with(FireworkEffect.Type.BALL_LARGE)
                .build();

        fireworkMeta.addEffect(effect);
        fireworkMeta.setPower(2); // 폭죽이 올라가는 높이를 설정합니다.
        firework.setFireworkMeta(fireworkMeta);
    }

    @Override
    public void run() {
        // 3회 폭죽을 발사합니다.
        for (int i = 0; i < 3; i++) {
            launchFirework(this.location);
        }
    }
}
