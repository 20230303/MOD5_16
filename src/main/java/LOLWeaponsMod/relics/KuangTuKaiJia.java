package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.utils.Json;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.potions.RegenPotion;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;

/**
 *   "LOLWeaponsMod:KuangTuKaiJia": {
 *     "NAME": "狂徒铠甲",
 *     "FLAVOR": "伤痕是它的勋章，浴血是它的仪式。",
 *     "DESCRIPTIONS": [
 *       "战斗开始时，获得X再生，X等于你已提升的生命上限次数。获得4点生命上限。"
 *     ]
 *   },
 */
public class KuangTuKaiJia extends CustomRelic implements CustomSavable<Integer> {
    public static final String ID = ModHelper.makePath("KuangTuKaiJia");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/KuangTuKaiJia.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/KuangTuKaiJia.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;

    private int regenAmount = 0; // 再生能力计数

    public KuangTuKaiJia() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
        this.counter = 0; // 初始计数为0
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new KuangTuKaiJia();
    }

    @Override
    public void onEquip() {
        // 装备时提升4点生命上限，触发一次计数+1
        AbstractDungeon.player.increaseMaxHp(4, true);
    }

    @Override
    public void atBattleStart() {
        if (regenAmount > 0) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new RegenPower(AbstractDungeon.player, regenAmount),
                    regenAmount
            ));
        }
    }

    // 关键修正：每次提升生命上限时固定+1，而非+amount
    public void increaseRegenCount() {
        this.regenAmount++;
        this.counter = regenAmount;
        this.flash();
    }

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "increaseMaxHp"
    )
    public static class KuangTuKaiJia$KuangTuKaiJiaPatch {
        @SpireInsertPatch(rloc = 15)
        public static void Insert(AbstractCreature __instance, int amount, boolean showEffect) {
            // 检查目标是否为玩家且拥有狂徒铠甲遗物
            if (__instance instanceof AbstractPlayer && ((AbstractPlayer)__instance).hasRelic(KuangTuKaiJia.ID)) {
                // 获取狂徒铠甲遗物实例
                KuangTuKaiJia relic = (KuangTuKaiJia)((AbstractPlayer)__instance).getRelic(KuangTuKaiJia.ID);

                // 增加再生值
                relic.increaseRegenCount();

                // 显示效果
                if (showEffect) {
                    AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(
                            __instance.hb.cX - __instance.animX,
                            __instance.hb.cY,
                            "+" + amount + " 再生",
                            Settings.GOLD_COLOR));
                }
            }
        }
    }

    @Override
    public Integer onSave() {
        return Integer.valueOf(regenAmount);
    }

    @Override
    public void onLoad(Integer loadedValue) {
        if (loadedValue != null) {
            regenAmount = loadedValue.intValue();
            this.counter = regenAmount;
        }
    }
}