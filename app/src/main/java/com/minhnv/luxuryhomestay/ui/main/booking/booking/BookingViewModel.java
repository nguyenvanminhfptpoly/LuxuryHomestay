package com.minhnv.luxuryhomestay.ui.main.booking.booking;

import android.text.TextUtils;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.Calendar;

public class BookingViewModel extends BaseViewModel<BookingNavigator> {
    private static final String TAG = "BookingViewModel";

    public BookingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public boolean isRequestValid(String InputDateStart,String InputDateEnd,String InputCountMember,String inputNameHomeStay,String inputAddress){
        if(TextUtils.isEmpty(InputDateStart)){
            return false;
        }else if(TextUtils.isEmpty(InputDateEnd)){
            return false;
        }else if(TextUtils.isEmpty(inputNameHomeStay)){
            return false;
        }else if(TextUtils.isEmpty(inputAddress)){
            return false;
        }
        return !TextUtils.isEmpty(InputCountMember);
    }

    public void booking(String DateStart,String DateEnd,String CountMember,String nameHomeStay,String address,int idUser,String username, String phonenumber){
        getCompositeDisposable().add(
                getDataManager().doServerBooking(new UserResponse.ServerBooking(DateStart,DateEnd,CountMember,nameHomeStay,address,idUser,username,phonenumber))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response.equals("Success")) {
                        getNavigator().onSuccess();
                    }else if(response.equals("Failed") || response.equals("Null")){
                        getNavigator().onFailed();
                    }
                },throwable -> {
                        getNavigator().handleError(throwable);
                        AppLogger.d(TAG, "booking: " + throwable);
                })
        );
    }

    public void didSelectCheckIn(){
        Calendar calendar = Calendar.getInstance();
        getNavigator().triggerCheckIn(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void didSelectCheckOut(){
        Calendar calendar = Calendar.getInstance();
        getNavigator().triggerCheckOut(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void ServerBooking(){
        getNavigator().doServerBooking();
    }
}
