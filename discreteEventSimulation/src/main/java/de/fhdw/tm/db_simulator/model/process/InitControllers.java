package model.process;

import model.IO_Controller;
import model.IO_Devices.HDD;
import model.IO_Devices.NCQ_HDD;
import model.IO_Devices.SSD;
import model.ProxyController;
import model.basic.Range;

public class InitControllers {
    private ProxyController proxyController = new ProxyController();
    private HDD hdd;
    private NCQ_HDD ncq_hdd;
    private SSD ssd;

    private Range<Long> hddRange = new Range<Long>(0l, 10000000l);
    private Range<Long> ncq_hddRange = new Range<Long>(10000000l,20000000l);
    private Range<Long> ssdRange = new Range<Long>(20000000l, 20000000l);

    public ProxyController init(IO_Controller controller){
        hdd = new HDD(hddRange, 250000l, 150000l, 50000l);
        ncq_hdd = new NCQ_HDD(ncq_hddRange, 300000l, 200000l, 40000l);
        ssd = new SSD(ssdRange, 500000l, 300000l, 3000l);

        controller.attachDevices(hdd, ncq_hdd, ssd);

        proxyController.attachControllers(controller);
        return proxyController;
    }
}
