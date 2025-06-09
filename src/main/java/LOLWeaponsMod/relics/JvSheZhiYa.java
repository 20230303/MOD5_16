package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;


/**遗物：巨蛇之牙
 * 触发：当玩家使用非全体攻击卡时
 * 效果：移除所有敌方的格挡
 * */
public class JvSheZhiYa extends CustomRelic {
    public static final String ID = ModHelper.makePath("JvSheZhiYa");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/JvSheZhiYa.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/JvSheZhiYa.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;//类型：罕见
    private static final LandingSound LANDING_SOUND = LandingSound.HEAVY;//声音：重击
    public JvSheZhiYa() {//注意修改这里 TODO
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new JvSheZhiYa();//修改这里 TODO
    }
    // 遗物效果，可在此处添加代码 TODO
    // 触发时机：当玩家使用非全体攻击卡时,移除所有敌方的格挡
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractPlayer p = AbstractDungeon.player;
        if (card.type == AbstractCard.CardType.ATTACK && card.target != AbstractCard.CardTarget.ALL_ENEMY && card.target != AbstractCard.CardTarget.ALL) {
            this.addToTop(new RemoveAllBlockAction(action.target, p));
        }
    }
}
