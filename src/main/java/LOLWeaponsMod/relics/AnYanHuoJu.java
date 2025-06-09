package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.SadisticPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

/**
 *   "LOLWeaponsMod:AnYanHuoJu": {
 *     "NAME": "黯炎火炬",
 *     "FLAVOR": "照亮前路的并非光明，而是吞噬灵魂的黑色火焰。",
 *     "DESCRIPTIONS": ["每当你对敌人造成负面状态，造成5点伤害"]
 *   },
 */
public class AnYanHuoJu extends CustomRelic {
    public static final String ID = ModHelper.makePath("AnYanHuoJu");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/AnYanHuoJu.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/AnYanHuoJu.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.RARE;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:魔法
    public AnYanHuoJu() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
            return new  AnYanHuoJu();
    }
    // 遗物效果，可在此处添加代码 TODO
    @Override
    public void atBattleStart() {
        //
        this.flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SadisticPower(AbstractDungeon.player, 5), 5));
    }

}
//以下是原版代码，已注释掉，仅供参考
//对敌人造成负面状态时，造成5点伤害
//    public SadisticNature() {
//     super("Sadistic Nature", cardStrings.NAME, "colorless/power/sadistic_nature", 0, cardStrings.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
//
//    this.baseMagicNumber = 5;
//     this.magicNumber = this.baseMagicNumber;
//   }
//
//  public void use(AbstractPlayer p, AbstractMonster m) {
//      addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p, (AbstractPower) new SadisticPower((AbstractCreature) p, this.magicNumber), this.magicNumber));
//  }
