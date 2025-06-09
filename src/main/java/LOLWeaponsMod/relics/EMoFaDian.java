package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *   "LOLWeaponsMod:EMoFaDian": {
 *     "NAME": "恶魔法典",
 *     "FLAVOR": "书页间翻涌着未完成的禁咒，等待献祭的墨水。",
 *     "DESCRIPTIONS": ["拾取时，移除一张卡牌"]
 *   },
 */
public class EMoFaDian extends CustomRelic {
    public static final String ID = ModHelper.makePath("EMoFaDian");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/EMoFaDian.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/EMoFaDian.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = RelicTier.DEPRECATED;//类型：弃置
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//音效：魔法
    public EMoFaDian() {
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new EMoFaDian();
    }
    // 遗物效果，可在此处添加代码 TODO
    private boolean cardsSelected = true;
    public void onEquip() {
        this.cardsSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator<AbstractCard> var2 = (AbstractDungeon.player.masterDeck.getPurgeableCards()).group.iterator();

        while (var2.hasNext()) {
            AbstractCard card = var2.next();
            tmp.addToTop(card);
        }
        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
        } else if (tmp.group.size() <= 1) {
            this.deleteCards(tmp.group);
        } else {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, this.DESCRIPTIONS[1], false, false, false, true);
        }

    }

    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1)
            deleteCards(AbstractDungeon.gridSelectScreen.selectedCards);
    }

    public void deleteCards(ArrayList<AbstractCard> group) {
        this.cardsSelected = true;
        float displayCount = 0.0F;
        Iterator<AbstractCard> i = group.iterator();
        while (i.hasNext()) {
            AbstractCard card = i.next();
            card.untip();
            card.unhover();
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, Settings.WIDTH / 3.0F + displayCount, Settings.HEIGHT / 2.0F));
            displayCount += Settings.WIDTH / 6.0F;
            AbstractDungeon.player.masterDeck.removeCard(card);
        }
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
    }

}
