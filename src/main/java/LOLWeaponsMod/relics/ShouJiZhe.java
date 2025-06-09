package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.GreedAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

/**
 *   "LOLWeaponsMod:ShouJiZhe": {
 *     "NAME": "收集者",
 *     "FLAVOR": "枪膛低语：'价值连城的，从不是物品，而是临终的恐惧'。",
 *     "DESCRIPTIONS": ["斩杀时，获得10金币"]
 *   },
 */
public class ShouJiZhe extends CustomRelic {
    public static final String ID = ModHelper.makePath("ShouJiZhe");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/ShouJiZhe.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/ShouJiZhe.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = RelicTier.UNCOMMON;//类型：稀有
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//音效：魔法
    public ShouJiZhe() {
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new ShouJiZhe();
    }
    // 遗物效果，可在此处添加代码 TODO
    // 检测敌人死亡时的伤害类型

    public void onMonsterDeath(AbstractMonster m) {
        if (!m.hasPower("Minion")) {
            AbstractDungeon.player.gainGold(10);
            this.flash();

            for(int i = 0; i < 5; ++i) {
                AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, m.hb.cX, m.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
            }
        }

    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount != 999) {
            if (!target.isPlayer && target instanceof AbstractMonster && !target.hasPower("Minion")) {
                float healthThreshold = (float)target.maxHealth * 0.05F;
                if ((float)(target.currentHealth - damageAmount) <= healthThreshold || (float)target.currentHealth <= healthThreshold) {
                    this.addToBot(new SFXAction("COLLECTOR"));
                    this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    target.currentHealth = 0;
                    target.healthBarUpdatedEvent();
                }
            }

        }
    }
}
