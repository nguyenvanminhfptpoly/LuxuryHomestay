package com.minhnv.luxuryhomestay.ui.main.favorite;

public interface FavoriteNavigator {
    void HandlerError(Throwable throwable);
    void onSuccess();
    void ServerLoadListFavorite();
}
