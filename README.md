# Android Drawer Template

The project is an example of a `RecycleView` adapter which can handle multiple view types. The RecyclerView component is used in the context of a drawer navigation. This project can be used as a template for a new app as it sets up some nice stuff like *RxAndroid*, *Dagger 2* and is implemented in language *Kotlin*.

<p align="center">
  <img src="/../screenshots/screenshot0.png?raw=true alt="Screenshot"/>
</p>

## Libraries

* RxAndroid
* Dagger 2
* Kotlin android extensions


## Implementation of RecycleView with multiple view type

The project has a class [DrawerAdapter](/../master/app/src/main/kotlin/views/adapters/drawer/DrawerAdapter.kt) which extends `RecyclerView.Adapter`. This adapter handles items of various types. First of all lets explain how the adapter item class and its corresponding view holder class look like.

View holder classes are defined as usual, nothing fancy.

```Kotlin
class ViewHolderHeader(drawerItemView: View) : RecyclerView.ViewHolder(drawerItemView) {
    val label: TextView = drawerItemView.findViewById(R.id.label) as TextView
}

class ViewHolderMedium(drawerItemView: View) : RecyclerView.ViewHolder(drawerItemView) {
    val icon: ImageView = drawerItemView.findViewById(R.id.icon) as ImageView
    val label: TextView = drawerItemView.findViewById(R.id.label) as TextView
}
```

Adapter item classes extend `AdapterItemBase<T>` where `T` is a view holder class. This base class has
a constructor which accepts `KClass<T>` The class info is used to get the view type of an adapter  item. See the [Tricks](#tricks). All we have to do is to override `bindViewHolder` which sets the data on a view holder (all type safely) Moreover we can mark an item as not selectable, assign id to it (because we need to identify which item has been clicked)

```Kotlin
class DrawerItemHeader(val label: String) : AdapterItemBase<ViewHolderHeader>(ViewHolderHeader::class) {
    override val selectable: Boolean = false

    override fun bindViewHolder(viewHolder: ViewHolderHeader) {
        viewHolder.label.setText(label)
    }
}

class DrawerItemMedium(override val id: Long, val icon: Drawable, val label: String,
                       val onClick: (Long) -> Unit, override val selectable: Boolean = true) : AdapterItemBase<ViewHolderMedium>(ViewHolderMedium::class) {
    override fun bindViewHolder(viewHolder: ViewHolderMedium) {
        viewHolder.icon.setImageDrawable(icon)
        viewHolder.label.setText(label)
        viewHolder.itemView.setOnClickListener { onClick(id) }
    }
}
```

*DrawerAdapter* uses interesting class [ViewHolderProvider](/../master/app/src/main/kotlin/views/adapters/ViewHolderProvider.kt) which holds information about the view holder class, layout id of an item and factory function which will create instance of a given view holder class.

During initialization all view holder classes should be registered in `ViewHolderProvider` in this way:

```Kotlin
viewHolderProvider.registerViewHolderFactory(ViewHolderMedium::class, R.layout.drawer_item_medium, { drawerItemView ->
    ViewHolderMedium(drawerItemView)
})

viewHolderProvider.registerViewHolderFactory(ViewHolderHeader::class, R.layout.drawer_item_header, { drawerItemView ->
    ViewHolderHeader(drawerItemView)
})
```

Now all is set. We have to compose list of items (of types `DrawerItemMedium`, `DrawerItemHeader`) and pass it to the adapter

```Kotlin
public class DrawerAdapter(val items: List<AdapterItem>) {
  ...
  override fun getItemViewType(position: Int): Int {
      return items[position].viewType
  }

  override fun getItemCount(): Int {
      return items.size()
  }

  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
      return viewHolderProvider.provideViewHolder(viewGroup, viewType)
  }

  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
      items[position].bindViewHolder(viewHolder)
  }

  override fun getItemId(position: Int): Long {
      return items[position].id
  }
  ...
}
```

The project contains adapter items of types like a header, separator, small and medium sized items. Also there is a more sophisticated item [DrawerItemSpinner](/../master/app/src/main/kotlin/views/adapters/drawer/drawerAdapterItems.kt) which can expand/collapse its list of sub items.

### Tricks

* *RecyclerView.Adapter* recognizes view types only by Integer so in this implementation view type is simply determined by a hashCode of specific view holder class. This way we can declaratively create multiple types.

* *DrawerItemSpinner* is a bit tricky. When a user expands a spinner all sub items are inserted to the model and the adapter is updated `notifyItemRangeInserted(startsFrom, subItems.size())` Likewise when a user collapse the spinner list all sub items are removed from the model and the adapter is updated `notifyItemRangeRemoved(startsFrom, subItems.size())`
The spinner needs access to the [AdapterItemsSupervisor](/../master/app/src/main/kotlin/views/adapters/AdapterItemsSupervisor.kt) to request changes on the adapter model.
