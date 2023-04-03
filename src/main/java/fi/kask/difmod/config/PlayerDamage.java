package fi.kask.difmod.config;

public class PlayerDamage {
    public boolean CanDie = true;
    public boolean DieToFire = true;

    public float FireDamageModifier = 1f;
    public float ProjectileDamageModifier = 1f;
    public float MagicDamageModifier = 1f;
    public float ExplosionDamageModifier = 1f;
    public float FallDamageModifier = 1f;
    public float OtherDamageModifier = 1f;

    public String logValues() {
        var sb = new StringBuilder();

        sb.append("Can die: ").append(CanDie ? "true" : "false").append(System.lineSeparator());
        sb.append("Dies to fire: ").append(CanDie ? "true" : "false").append(System.lineSeparator());

        sb.append("Fire damage modifier: ").append(FireDamageModifier).append(System.lineSeparator());
        sb.append("Projectile damage modifier: ").append(FireDamageModifier).append(System.lineSeparator());
        sb.append("Magic damage modifier: ").append(FireDamageModifier).append(System.lineSeparator());
        sb.append("Explosion damage modifier: ").append(FireDamageModifier).append(System.lineSeparator());
        sb.append("Fall damage modifier: ").append(FireDamageModifier).append(System.lineSeparator());
        sb.append("Other damage modifier: ").append(FireDamageModifier).append(System.lineSeparator());

        return sb.toString();
    }
}