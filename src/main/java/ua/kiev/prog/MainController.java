package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/SpringMVC_war_exploded")
public class MainController {

	@Autowired
	private AdvDAO advDAO;

	@RequestMapping("/")
	public ModelAndView listAdvs() {
		return new ModelAndView("index", "advs", advDAO.list());
	}

	@RequestMapping("/restore_deleted")
	public ModelAndView listAdvsWithDeleted() {
		return new ModelAndView("restore", "advs", advDAO.listDeleted());
	}


	@RequestMapping(value = "/add_page", method = RequestMethod.POST)
	public String addPage(Model model) {
		return "add_page";
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam(value="pattern") String pattern) {
		return new ModelAndView("index", "advs", advDAO.list(pattern));
	}

	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam(value="id") long id) {
		advDAO.delete(id,false);
		return new ModelAndView("index", "advs", advDAO.list());

	}
/*
	@RequestMapping("/delete_pages")
	public ModelAndView delete_pages(
		@RequestParam(value = "checkDelete") long[] selectPage) {
			for (int i = 0; i < selectPage.length; i++) {
				if (selectPage[i]==1)
				advDAO.delete(i);
			}

			return new ModelAndView("index", "advs", advDAO.list());
		}
*/

	@RequestMapping("/delete_pages")
	public ModelAndView deletePages(


			@RequestParam(required = false,value = "selected") long[] selected,@RequestParam(required = false,value = "Basket") String[] basket) {

		if (selected!=null)
		for (long i:selected) {
			advDAO.delete(i,(basket!=null));
		}
		return new ModelAndView("index", "advs", advDAO.list());
	}


	@RequestMapping("/undelete")
	public ModelAndView undeletePages(


			@RequestParam(required = false,value = "selected") long[] selected) {

		if (selected!=null)
			for (long i:selected) {
				advDAO.undelete(i);
			}
		return new ModelAndView("index", "advs", advDAO.list());
	}



	@RequestMapping("/image/{file_id}")
	public void getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("file_id") long fileId) {
		try {
			byte[] content = advDAO.getPhoto(fileId);
			response.setContentType("image/jpg");
			response.getOutputStream().write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addAdv(@RequestParam(value="name") String name,
						 @RequestParam(value="shortDesc") String shortDesc,
						 @RequestParam(value="longDesc", required=false) String longDesc,
						 @RequestParam(value="phone") String phone,
						 @RequestParam(value="price") double price,
						 @RequestParam(value="photo") MultipartFile photo,
						 HttpServletRequest request,
						 HttpServletResponse response)
	{
		try {
			Advertisement adv = new Advertisement(
					name, shortDesc, longDesc, phone, price,
					photo.isEmpty() ? null : new Photo(photo.getOriginalFilename(), photo.getBytes())
			);
			advDAO.add(adv);
			return new ModelAndView("index", "advs", advDAO.list());
		} catch (IOException ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
	}
}