package me.yoshiro09.simpleplayerpee.api;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.yoshiro09.simpleplayerpee.Main;
import me.yoshiro09.simpleplayerpee.api.customevents.PlayerWaterLevelChangedEvent;

public class PeeingPlayer {
	private static final double MAX_WATER_VALUE = 10.0D;

	private Player player;

	private double waterLevel;

	private boolean peeMode = false;

	private boolean superPee = false;

	private static Main plugin = Main.getInstance();

	private int r = 255;
	private int g = 255;
	private int b = 0;

	private Material peeMaterial = Material.YELLOW_DYE;

	public PeeingPlayer(Player player) {
		this.player = player;
	}

	public PeeingPlayer(Player player, double thirstyLevel) {
		this.player = player;
		this.waterLevel = thirstyLevel;
	}

	/**
	 * Riporta il giocatore gestito dalla classe.
	 * 
	 * @return Player della classe
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Riporta la quantità d'acqua massima che un giocatore può bere senza iniziare
	 * a subire danno.
	 * 
	 * @return Quantità d'acqua massima
	 */
	public static double getMaxWaterValue() {
		return MAX_WATER_VALUE;
	}

	/**
	 * Riporta la quantità d'acqua presente nel corpo del giocatore.
	 * 
	 * @return Quantità d'acqua nel giocatore
	 */
	public double getWaterLevel() {
		return this.waterLevel;
	}

	/**
	 * Imposta ad un giocatore la quantità d'acqua nel suo corpo.
	 * 
	 * @param Quantità in double
	 */
	public void setWaterLevel(double thirstyLevel) {
		this.waterLevel = thirstyLevel;
		
		callPlayerWaterLevelChangedEvent();
	}

	/**
	 * Aggiunge la quantità d'acqua specificata a quella già presente nel corpo del
	 * giocatore.
	 * 
	 * @param Quantità in double
	 */
	public void addWaterLevel(double value) {
		this.waterLevel += value;
		if (this.waterLevel > 10.0D)
			this.waterLevel = 10.0D;
		
		callPlayerWaterLevelChangedEvent();
	}

	/**
	 * Rimuove la quantità d'acqua specificata da quella già presente nel corpo del
	 * giocatore.
	 * 
	 * @param Quantità in double
	 */
	public void removeWaterLevel(double value) {
		this.waterLevel -= value;
		if (this.waterLevel < 0)
			this.waterLevel = 0;

		callPlayerWaterLevelChangedEvent();
	}
	
	/**
	 * Dà true se il giocatore ha la SuperPee attiva, false se ce l'ha disattivata.
	 * 
	 * @return Boolean
	 */
	public boolean hasSuperPeeActive() {
		return superPee;
	}
	
	/**
	 * Attiva e disattiva la SuperPee per un giocatore.
	 * 
	 * @param true per attivarla, false per disattivarla
	 */
	public void setSuperPee(boolean superPee) {
		this.superPee = superPee;
	}
	
	/**
	 * Riporta il valore del colore delle particelle in RGB.
	 * 
	 * @return valori RGB delle particelle
	 */
	public String getParticlesRGB() {
		return r + "," + g + "," + b;
	}

	/**
	 * Imposta il colore delle particelle in RGB.
	 * 
	 * @param Int per il colore rosso
	 * @param Int per il colore verde
	 * @param Int per il colore blu
	 */
	public void setParticlesRGB(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Riporta il materiale di cui è composto il piscio.
	 * 
	 * @return Materiale piscio
	 */
	public Material getPeeMaterial() {
		return peeMaterial;
	}
	
	/**
	 * Imposta il materiale con cui sarà fatto il piscio.
	 * 
	 * @param Materiale per il piscio
	 */
	public void setPeeMaterial(Material peeMaterial) {
		this.peeMaterial = peeMaterial;
	}

	/**
	 * Riporta se il giocatore ha la modalità PeeMode attiva.
	 * 
	 * @return Boolean
	 */
	public boolean hasPeeModeActive() {
		return this.peeMode;
	}

	/**
	 * Imposta al giocatore lo stato della modalità PeeMode.
	 * 
	 * @param true per attivarla, false per disattivarla
	 */
	public void setPeeMode(boolean peeMode) {
		this.peeMode = peeMode;
	}

	/**
	 * Fa pisciare il giocatore.
	 */
	public void makePee() {
		if (this.waterLevel < 1.0D) {
			return;
		}
		
		throwPee();
		this.waterLevel--;
		
		Main.getDebuffCheck().check(this);
	}
	
	/**
	 * Fa usare la SuperPee al giocatore.
	 */
	public void makeSuperPee() {
		throwPee();
		this.waterLevel -= 6;
		
		Main.getDebuffCheck().check(this);
	}

	/**
	 * Fa pisciare il giocatore solo se shiftato.
	 */
	public void makePeeShifted() {
		(new BukkitRunnable() {
			public void run() {
				if (PeeingPlayer.this.player.isSneaking()) {
					if (superPee) {
						if (waterLevel < 6) {
							superPee = false;
						} else {
							makeSuperPee();
						}
					} else {
						if (PeeingPlayer.this.waterLevel >= 1.0D) {
							PeeingPlayer.this.makePee();
						} else {
							this.cancel();
						}
					}
				} else {
					this.cancel();
				}
			}
		}).runTaskTimer((Plugin) plugin, 0L, 10L);
	}

	private void throwPee() {
		if (!superPee) {
			setParticlesRGB(255, 255, 0);
			setPeeMaterial(Material.YELLOW_DYE);
		} else {
			setParticlesRGB(230, 0, 0);
			setPeeMaterial(Material.RED_DYE);
		}
		
		Location pLoc = this.player.getLocation();
		pLoc.setY(pLoc.getY() + 0.2D);
		pLoc.setX(pLoc.getX() - 0.5);
		pLoc.setZ(pLoc.getZ() - 0.5);
		ItemStack peeItem = new ItemStack(peeMaterial);
		peeItem = plugin.getNBTWrapper().setNBTTag(peeItem, "SimplePeeNBT");
		
		Item item = pLoc.getWorld().dropItemNaturally(pLoc, peeItem);
		Vector v = pLoc.getDirection().multiply((superPee ? 0.6D : 0.3D));
		v.setY(0.3D);
		item.setVelocity(v);
		
		if (!superPee) {
			deletePeeItem((Entity) item);
			return;
		} else {
			deleteSuperPeeItem((Entity) item);
		}
		
	}

	private void deletePeeItem(final Entity thrownPeeItem) {
		(new BukkitRunnable() {
			double seconds = 0.0D;

			public void run() {
				if (thrownPeeItem.isOnGround()) {
					if (this.seconds >= 7.0D) {
						thrownPeeItem.remove();
						this.cancel();
					}

					this.seconds += 0.25D;
				}

				spawnParticles(thrownPeeItem);
			}
		}).runTaskTimer((Plugin) plugin, 0L, 5L);
	}

	private void deleteSuperPeeItem(final Entity thrownPeeItem) {
		superPee = false;

		(new BukkitRunnable() {
			public void run() {
				if (thrownPeeItem.isOnGround()) {
					Bukkit.getWorld(thrownPeeItem.getLocation().getWorld().getName())
							.createExplosion(thrownPeeItem.getLocation(), 3F);
					thrownPeeItem.remove();
					this.cancel();
				}

				spawnParticles(thrownPeeItem);
			}
		}).runTaskTimer((Plugin) plugin, 0L, 5L);
	}

	private void spawnParticles(final Entity thrownPeeItem) {
		Bukkit.getWorld(thrownPeeItem.getLocation().getWorld().getName()).spawnParticle(Particle.REDSTONE,
				thrownPeeItem.getLocation().getX(), thrownPeeItem.getLocation().getY(),
				thrownPeeItem.getLocation().getZ(), 20, 0.1D, 0.0D, 0.1D,
				new Particle.DustOptions(Color.fromRGB(r, g, b), 1.0F));
	}
	
	/**
	 * Riporta un boolean in base ai salvataggi del giocatore.
	 * 
	 * @return true se il giocatore ha dei salvataggi, false se non ce li ha
	 */
	public boolean hasData() {
		return (plugin.getPlayerdataConfig().getString(player.getUniqueId().toString() + ".waterLevel") == null ? false : true);
	}
	
	/**
	 * Salva le informazioni riguardanti il giocatore nel file playerdata.yml.
	 */
	public void saveData() {
		plugin.getPlayerdataConfig().set(player.getUniqueId().toString() + ".waterLevel", waterLevel);
		savePlayerdataConfig();
	}
	
	/**
	 * Carica i dati di un giocatore.
	 */
	public void loadData() {
		this.waterLevel = plugin.getPlayerdataConfig().getDouble(player.getUniqueId().toString() + ".waterLevel");
	}
	
	/**
	 * Controlla se il giocatore ha il debuff in base al suo livello d'acqua.
	 *  
	 * @return Il messaggio da inviare al giocatore (poca-acqua, troppa-acqua)
	 */
	public String checkSlowness() {
		if (waterLevel <= 2) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9999999, 2));
			return plugin.getLanguageConfig().getString("debuff.poca-acqua");
		} else if (waterLevel >= 8) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9999999, 2));
			return plugin.getLanguageConfig().getString("debuff.troppa-acqua");
		}
		
		PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.SLOW);
		
		if (potionEffect != null) {
			if (potionEffect.getAmplifier() >= 2) {
				player.removePotionEffect(potionEffect.getType());
			}
		}
		
		return null;
	}
	
	/**
	 * Sovrascrive/Crea i salvataggi del giocatore nel file playerdata.yml.
	 */
	public void createData() {
		this.waterLevel = 5;
		plugin.getConfig().set(player.getUniqueId() + ".waterLevel", waterLevel);
	}
	
	private void savePlayerdataConfig() {
		try {
			plugin.getPlayerdataConfig().save(plugin.getPlayerdataFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	private void callPlayerWaterLevelChangedEvent() {
		PlayerWaterLevelChangedEvent e = new PlayerWaterLevelChangedEvent(this);
		Bukkit.getServer().getPluginManager().callEvent(e);
	}
}