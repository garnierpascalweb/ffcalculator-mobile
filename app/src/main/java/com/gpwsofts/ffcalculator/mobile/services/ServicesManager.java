package com.gpwsofts.ffcalculator.mobile.services;

import android.content.res.Resources;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.common.reader.AssetReaderProvider;
import com.gpwsofts.ffcalculator.mobile.services.device.IDeviceService;
import com.gpwsofts.ffcalculator.mobile.services.device.SimpleDeviceService;
import com.gpwsofts.ffcalculator.mobile.services.grid.IGridService;
import com.gpwsofts.ffcalculator.mobile.services.grid.SimpleGridService;
import com.gpwsofts.ffcalculator.mobile.services.logo.ILogoService;
import com.gpwsofts.ffcalculator.mobile.services.logo.SimpleLogoService;
import com.gpwsofts.ffcalculator.mobile.services.network.INetworkService;
import com.gpwsofts.ffcalculator.mobile.services.network.SimpleNetworkService;
import com.gpwsofts.ffcalculator.mobile.services.pos.IPosHttpService;
import com.gpwsofts.ffcalculator.mobile.services.pos.SimplePosHttpService;
import com.gpwsofts.ffcalculator.mobile.services.pts.IPtsHttpService;
import com.gpwsofts.ffcalculator.mobile.services.pts.SimplePtsHttpService;
import com.gpwsofts.ffcalculator.mobile.services.report.AsyncReportHttpService;
import com.gpwsofts.ffcalculator.mobile.services.report.IReportHttpService;
import com.gpwsofts.ffcalculator.mobile.services.report.SimpleReportHttpService;
import com.gpwsofts.ffcalculator.mobile.services.town.ITownService;
import com.gpwsofts.ffcalculator.mobile.services.town.SimpleTownService;
import com.gpwsofts.ffcalculator.mobile.services.vue.IVueService;
import com.gpwsofts.ffcalculator.mobile.services.vue.VueService;

/**
 * Gestion des Services
 *
 * @since 1.0.0
 */
public class ServicesManager {

    private static final String TAG_NAME = "ServicesManager";

    /**
     * Service des logos
     */
    private ILogoService logoService = null;

    /**
     * Service http des points
     */
    private IPtsHttpService ptsService = null;

    /**
     * Service http de classement
     */
    private IPosHttpService posService = null;
    /**
     * Service concernant l'état du reseau
     */
    private INetworkService networkService = null;
    /**
     *
     */
    private IGridService gridService = null;
    private ITownService townService = null;


    /**
     * Service de vue
     * @since 1.0.0
     */
    private IVueService vueService;
    private IDeviceService deviceService = null;
    private IReportHttpService reportService = null;
    private IReportHttpService asyncReportService = null;

    /**
     * Constructeur de ServiceManager
     *
     * @param application l'application
     */
    public ServicesManager(FFCalculatorApplication application) {
        if (application.isServicesManagerAlreadyExist()) {
            throw new ExceptionInInitializerError("ServicesManager deja instancié pour FFCalculatorApplication");
        }
    }

    public final ILogoService getLogoService(Resources res) {
        if (null == logoService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de ILogoService");
            logoService = new SimpleLogoService(res);
        } else {
            LogUtils.d(TAG_NAME, "recuperation dune instance existante de ILogoService");
        }
        return logoService;
    }

    public final IPtsHttpService getPtsService() {
        if (null == ptsService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de IPtsService");
            ptsService = new SimplePtsHttpService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation dune instance existante de IPtsService");
        }
        return ptsService;
    }

    public final IPosHttpService getPosService() {
        if (null == posService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de IPosService");
            posService = new SimplePosHttpService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de IPosService");
        }
        return posService;
    }

    public final INetworkService getNetworkService() {
        if (null == networkService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de INetworkService");
            networkService = new SimpleNetworkService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de INetworkService");
        }
        return networkService;
    }

    public final IGridService getGridService() {
        if (null == gridService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de IGridService");
            gridService = new SimpleGridService(new AssetReaderProvider(FFCalculatorApplication.instance.getApplicationContext()));
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de IGridService");
        }
        return gridService;
    }

    public final ITownService getTownService() {
        if (null == townService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de ITownService");
            townService = new SimpleTownService(new AssetReaderProvider(FFCalculatorApplication.instance.getApplicationContext()));
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de ITownService");
        }
        return townService;
    }

    /**
     * @since 1.0.0
     * @return un singleton de service de vue
     */
    public final IVueService getVueService(){
        if (null == vueService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de IVueService");
            vueService = new VueService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de IVueService");
        }
        return vueService;
    }

    public final IDeviceService getDeviceService(){
        if (null == deviceService){
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de IDeviceService");
            deviceService = new SimpleDeviceService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de IDeviceService");
        }
        return deviceService;
    }

    public final IReportHttpService getReportService(){
        if (null == reportService){
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance SimpleReportHttpService de IReportService");
            reportService = new SimpleReportHttpService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante SimpleReportHttpService de IReportService");
        }
        return reportService;
    }

    public final IReportHttpService getAsyncReportService(){
        if (null == asyncReportService){
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance asyncReportService de IReportService");
            asyncReportService = new AsyncReportHttpService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante asyncReportService de IReportService");
        }
        return asyncReportService;
    }

    /**
     * @since 1.0.0
     * Désallocation de tous les services
     */
    public final void unbindAll(){
        if (logoService != null){
            logoService.clean();
            logoService = null;
        }
        if (ptsService != null){
            ptsService.clean();
            ptsService = null;
        }
        if (posService != null){
            posService.clean();
            posService = null;
        }
        if (networkService != null){
            networkService.clean();
            networkService = null;
        }
        if (gridService != null){
            gridService.clean();
            gridService = null;
        }
        if (townService != null){
            townService.clean();
            townService = null;
        }
        if (vueService != null){
            vueService.clean();
            vueService = null;
        }
        if (deviceService != null){
            deviceService.clean();
            deviceService = null;
        }
        if (reportService != null){
            reportService.clean();
            reportService = null;
        }
    }

}
